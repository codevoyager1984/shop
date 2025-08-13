package com.cool.modules.app.controller.admin;

import static com.cool.modules.app.entity.table.AppFeedbackEntityTableDef.APP_FEEDBACK_ENTITY;
import static com.cool.modules.base.entity.sys.table.BaseSysUserEntityTableDef.BASE_SYS_USER_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.enums.QueryModeEnum;
import com.cool.modules.app.entity.AppFeedbackEntity;
import com.cool.modules.app.service.AppFeedbackService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 意见反馈
 */
@Tag(name = "意见反馈", description = "意见反馈")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminAppFeedbackController extends BaseController<AppFeedbackService, AppFeedbackEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields(APP_FEEDBACK_ENTITY.CONTACT,
                USER_INFO_ENTITY.NICK_NAME, BASE_SYS_USER_ENTITY.NAME)
            .queryWrapper(QueryWrapper.create()
                .select(APP_FEEDBACK_ENTITY.ALL_COLUMNS,
                    USER_INFO_ENTITY.NICK_NAME.as("nickName"),
                    BASE_SYS_USER_ENTITY.NAME.as("handlerName"))
                .from(APP_FEEDBACK_ENTITY)
                .leftJoin(USER_INFO_ENTITY)
                .on(APP_FEEDBACK_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID))
                .leftJoin(BASE_SYS_USER_ENTITY)
                .on(APP_FEEDBACK_ENTITY.HANDLER_ID.eq(BASE_SYS_USER_ENTITY.ID)))
            .queryModeEnum(QueryModeEnum.CUSTOM)
            .transform(o -> {
                // 类型转换
                Map map = (Map) o;
                Object json = map.get(APP_FEEDBACK_ENTITY.IMAGES.getName());
                if (ObjUtil.isNotEmpty(json)) {
                    map.put(APP_FEEDBACK_ENTITY.IMAGES.getName(), new JSONArray(json));
                }
            })
        );
    }
}