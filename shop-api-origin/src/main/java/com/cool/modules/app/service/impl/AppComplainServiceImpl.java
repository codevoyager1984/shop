package com.cool.modules.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.base.ModifyEnum;
import com.cool.core.enums.UserTypeEnum;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.app.entity.AppComplainEntity;
import com.cool.modules.app.mapper.AppComplainMapper;
import com.cool.modules.app.service.AppComplainService;
import org.springframework.stereotype.Service;

/**
 * 举报投诉
 */
@Service
public class AppComplainServiceImpl extends BaseServiceImpl<AppComplainMapper, AppComplainEntity> implements AppComplainService {

    @Override
    public void modifyBefore(JSONObject requestParams, AppComplainEntity entity, ModifyEnum type) {
        if (ObjUtil.isNotEmpty(entity) && ObjUtil.equals(ModifyEnum.UPDATE, type)) {
            // 后台更新人id
            if (ObjUtil.equals(CoolSecurityUtil.getCurrentUserType(), UserTypeEnum.ADMIN)) {
                entity.setHandlerId(CoolSecurityUtil.getCurrentUserId());
            }
        }
    }
}