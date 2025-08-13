package com.cool.modules.cs.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.cs.entity.CsConnEntity;
import com.cool.modules.cs.entity.CsSessionEntity;
import com.cool.modules.cs.mapper.CsConnMapper;
import com.cool.modules.cs.service.CsConnService;
import com.cool.modules.cs.service.CsSessionService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 客服连接
 */
@Service
@RequiredArgsConstructor
public class CsConnServiceImpl extends BaseServiceImpl<CsConnMapper, CsConnEntity> implements CsConnService {
    private final CsSessionService csSessionService;
    @Override
    public void binding(boolean isAdmin, Long userId, String connId) {
        CsConnEntity csConnEntity = getCsConnEntity(isAdmin, userId);
        if (ObjUtil.isNotEmpty(csConnEntity)) {
            csConnEntity.setConnId(connId);
            csConnEntity.updateById();
        } else {
            csConnEntity = new CsConnEntity();
            csConnEntity.setUserId(userId);
            csConnEntity.setConnId(connId);
            csConnEntity.setType(isAdmin ? 1 : 0);
            csConnEntity.save();
        }
    }

    private CsConnEntity getCsConnEntity(boolean isAdmin, Long userId) {
        return mapper.selectOneByQuery(QueryWrapper.create()
            .eq(CsConnEntity::getUserId, userId)
            .eq(CsConnEntity::getType, isAdmin ? 1 : 0));
    }

    @Override
    public String getConnId(boolean isAdmin, Long sessionId) {
        CsSessionEntity csSessionEntity = csSessionService.getById(sessionId);
        if (ObjUtil.isEmpty(csSessionEntity)) {
            return null;
        }
        CsConnEntity csConnEntity = getCsConnEntity(isAdmin, csSessionEntity.getUserId());
        return ObjUtil.isNotEmpty(csConnEntity) ? csConnEntity.getConnId() : null;
    }
}