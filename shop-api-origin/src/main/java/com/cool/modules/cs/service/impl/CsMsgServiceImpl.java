package com.cool.modules.cs.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.base.entity.sys.BaseSysUserEntity;
import com.cool.modules.base.service.sys.BaseSysUserService;
import com.cool.modules.cs.entity.CsMsgEntity;
import com.cool.modules.cs.entity.CsSessionEntity;
import com.cool.modules.cs.mapper.CsMsgMapper;
import com.cool.modules.cs.service.CsConnService;
import com.cool.modules.cs.service.CsMsgService;
import com.cool.modules.cs.service.CsSessionService;
import com.cool.modules.cs.socket.SocketIOSessionManager;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.service.UserInfoService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 客服消息
 */
@Service
@RequiredArgsConstructor
public class CsMsgServiceImpl extends BaseServiceImpl<CsMsgMapper, CsMsgEntity> implements CsMsgService {

    private final SocketIOSessionManager socketIOSessionManager;

    private final CsSessionService csSessionService;

    private final UserInfoService userInfoService;

    private final BaseSysUserService baseSysUserService;

    private final CsConnService csConnService;

    @Override
    public Object read(List<Long> msgIds) {
        CoolPreconditions.checkEmpty(msgIds);
        return UpdateChain.of(CsMsgEntity.class)
            .set(CsMsgEntity::getStatus, 1)
            .in(CsMsgEntity::getId, msgIds)
            .update();
    }

    @Override
    public Object unreadCount(Long userId, int type) {
        return mapper.selectCountByQuery(QueryWrapper.create()
            .eq(CsMsgEntity::getUserId, userId, ObjUtil.isNotEmpty(userId)).eq(CsMsgEntity::getType, type));
    }

    @Override
    public void send(JSONObject jsonObject) {
        Long sessionId = jsonObject.get("sessionId", Long.class);
        boolean isAdmin = jsonObject.get("isAdmin", Boolean.class);
        CoolPreconditions.checkEmpty(sessionId);
        CsSessionEntity csSessionEntity = csSessionService.getById(sessionId);
        CsMsgEntity csMsgEntity = jsonObject.toBean(CsMsgEntity.class);
        if (isAdmin) {
            // 后台人员发送
            csMsgEntity.setType(1);
        } else {
            // 用户发送
            csMsgEntity.setType(0);
            if (ObjUtil.isEmpty(csSessionEntity)) {
                csSessionEntity = new CsSessionEntity();
                csSessionEntity.setUserId(jsonObject.get("userId", Long.class));
            }
        }
        // 保存消息
        csMsgEntity.save();
        // 未读消息数
        csSessionEntity.setAdminUnreadCount(count(QueryWrapper.create().eq(CsMsgEntity::getSessionId, sessionId)
            .eq(CsMsgEntity::getStatus, 0).eq(CsMsgEntity::getType, 0)));
        // 更新最后一条消息
        csSessionEntity.setLastMsg(csMsgEntity);
        csSessionEntity.saveOrUpdate();
        // 完善消息内容
        if (ObjUtil.equals(csMsgEntity.getType(), 0)) {
            // app 端消息
            UserInfoEntity userInfoEntity = userInfoService.getById(csMsgEntity.getUserId());
            csMsgEntity.setNickName(userInfoEntity.getNickName());
            csMsgEntity.setAvatarUrl(userInfoEntity.getAvatarUrl());
        } else {
            // 后台 消息
            BaseSysUserEntity baseSysUserEntity = baseSysUserService.getById(csMsgEntity.getUserId());
            csMsgEntity.setNickName(baseSysUserEntity.getNickName());
            csMsgEntity.setAvatarUrl(baseSysUserEntity.getHeadImg());
        }
        // 发送给客户端
        if (isAdmin) {
            // 获得连接ID
            String connId = csConnService.getConnId(false, csMsgEntity.getSessionId());
            SocketIOClient client = socketIOSessionManager.getSession(connId);
            if (ObjUtil.isEmpty(client)) {
                // 客户连接已关闭
                return;
            }
            // 发送消息
            client.sendEvent("msg", csMsgEntity);
        } else {
            // 广播给所有的后端客服
            SpringUtil.getBean(SocketIOServer.class).getBroadcastOperations().sendEvent("msg", csMsgEntity);
        }
    }
}