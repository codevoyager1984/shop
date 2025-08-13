package com.cool.modules.app.service;

import com.cool.core.base.BaseService;
import com.cool.modules.app.entity.AppVersionEntity;

/**
 * 应用版本
 */
public interface AppVersionService extends BaseService<AppVersionEntity> {

    Object check(String version, Integer type);
}
