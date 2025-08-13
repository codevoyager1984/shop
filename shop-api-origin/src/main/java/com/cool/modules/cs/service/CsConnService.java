package com.cool.modules.cs.service;

import com.cool.core.base.BaseService;
import com.cool.modules.cs.entity.CsConnEntity;

/**
 * 客服连接
 */
public interface CsConnService extends BaseService<CsConnEntity> {
    /**
     * 连接绑定
     */
    void binding(boolean isAdmin, Long userId, String connId);

    /**
     * 获得连接ID
     * @param isAdmin
     * @param sessionId
     */
    String getConnId(boolean isAdmin, Long sessionId);
}
