package com.cool.modules.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.base.ModifyEnum;
import com.cool.core.enums.UserTypeEnum;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.app.entity.AppFeedbackEntity;
import com.cool.modules.app.mapper.AppFeedbackMapper;
import com.cool.modules.app.service.AppFeedbackService;
import org.springframework.stereotype.Service;

/**
 * 意见反馈
 */
@Service
public class AppFeedbackServiceImpl extends BaseServiceImpl<AppFeedbackMapper, AppFeedbackEntity> implements AppFeedbackService {
    @Override
    public void modifyBefore(JSONObject requestParams, AppFeedbackEntity entity, ModifyEnum type) {
        if (ObjUtil.isNotEmpty(entity) && ObjUtil.equals(ModifyEnum.UPDATE, type)) {
            // 后台更新人id
            if (ObjUtil.equals(CoolSecurityUtil.getCurrentUserType(), UserTypeEnum.ADMIN)) {
                entity.setHandlerId(CoolSecurityUtil.getCurrentUserId());
            }
        }
    }
}