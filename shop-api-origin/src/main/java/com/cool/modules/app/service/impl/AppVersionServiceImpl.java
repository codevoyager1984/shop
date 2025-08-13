package com.cool.modules.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.app.entity.AppVersionEntity;
import com.cool.modules.app.mapper.AppVersionMapper;
import com.cool.modules.app.service.AppVersionService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 应用版本
 */
@Service
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersionMapper, AppVersionEntity> implements AppVersionService {

    @Override
    public Object check(String version, Integer type) {
        AppVersionEntity appVersionEntity = mapper.selectOneByQuery(
            QueryWrapper.create().eq(AppVersionEntity::getType, type)
                .eq(AppVersionEntity::getStatus, 1));
        if (appVersionEntity != null
            && StrUtil.compareVersion(appVersionEntity.getVersion(), version) > 0) {
            return appVersionEntity;
        }
        return null;
    }
}