package com.cool.modules.cs.service;

import com.cool.core.base.BaseService;
import com.cool.modules.cs.entity.CsSessionEntity;

/**
 * 客服会话
 */
public interface CsSessionService extends BaseService<CsSessionEntity> {

    /**
     * 会话详情
     */
    Object detail(Long userId);

    /**
     * 创建会话
     */
    Object create(Long userId);
}
