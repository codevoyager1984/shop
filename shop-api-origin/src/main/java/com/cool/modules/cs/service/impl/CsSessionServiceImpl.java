package com.cool.modules.cs.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.cs.entity.CsSessionEntity;
import com.cool.modules.cs.mapper.CsSessionMapper;
import com.cool.modules.cs.service.CsSessionService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 客服会话
 */
@Service
public class CsSessionServiceImpl extends BaseServiceImpl<CsSessionMapper, CsSessionEntity> implements CsSessionService {

    @Override
    public Object detail(Long userId) {
        return mapper.selectOneByQuery(QueryWrapper.create().eq(CsSessionEntity::getUserId, userId));
    }

    @Override
    public Object create(Long userId) {
        Object detail = detail(userId);
        if (ObjUtil.isNotEmpty(detail)) {
            return detail;
        }
        CsSessionEntity csSessionEntity = new CsSessionEntity();
        csSessionEntity.setUserId(userId);
        csSessionEntity.save();
        return csSessionEntity;
    }
}