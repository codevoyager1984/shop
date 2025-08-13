package com.cool.modules.cs.service;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseService;
import com.cool.modules.cs.entity.CsMsgEntity;
import java.util.List;

/**
 * 客服消息
 */
public interface CsMsgService extends BaseService<CsMsgEntity> {

    /**
     * 消息标记为已读
     * @param msgIds
     * @return
     */
    Object read(List<Long> msgIds);

    /**
     * 获取未读消息数
     */
    Object unreadCount(Long userId, int type);

    /**
     * 发送消息
     * @param jsonObject
     */
    void send(JSONObject jsonObject);
}
