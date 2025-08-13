package com.cool.modules.app.controller.admin;

import static com.cool.modules.app.entity.table.AppComplainEntityTableDef.APP_COMPLAIN_ENTITY;
import static com.cool.modules.base.entity.sys.table.BaseSysUserEntityTableDef.BASE_SYS_USER_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.enums.QueryModeEnum;
import com.cool.modules.app.entity.AppComplainEntity;
import com.cool.modules.app.service.AppComplainService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 举报投诉
 */
@Tag(name = "举报投诉", description = "举报投诉")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminAppComplainController extends BaseController<AppComplainService, AppComplainEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    setPageOption(
        createOp()
            .keyWordLikeFields(
                APP_COMPLAIN_ENTITY.CONTACT, USER_INFO_ENTITY.NICK_NAME, BASE_SYS_USER_ENTITY.NAME)
            .queryWrapper(
                QueryWrapper.create()
                    .select(
                        APP_COMPLAIN_ENTITY.ALL_COLUMNS,
                        USER_INFO_ENTITY.NICK_NAME.as("nickName"),
                        BASE_SYS_USER_ENTITY.NAME.as("handlerName"))
                    .from(APP_COMPLAIN_ENTITY)
                    .leftJoin(USER_INFO_ENTITY)
                    .on(APP_COMPLAIN_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID))
                    .leftJoin(BASE_SYS_USER_ENTITY)
                    .on(APP_COMPLAIN_ENTITY.HANDLER_ID.eq(BASE_SYS_USER_ENTITY.ID)))
            .queryModeEnum(QueryModeEnum.CUSTOM)
            .transform(
                o -> {
                  // 类型转换
                  Map map = (Map) o;
                  Object json = map.get(APP_COMPLAIN_ENTITY.IMAGES.getName());
                  if (ObjUtil.isNotEmpty(json)) {
                    map.put(APP_COMPLAIN_ENTITY.IMAGES.getName(), new JSONArray(json));
                  }
                }));
    }
}