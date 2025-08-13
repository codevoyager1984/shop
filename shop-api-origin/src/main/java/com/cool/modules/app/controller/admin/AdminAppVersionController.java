package com.cool.modules.app.controller.admin;

import static com.cool.modules.app.entity.table.AppVersionEntityTableDef.APP_VERSION_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.app.entity.AppVersionEntity;
import com.cool.modules.app.service.AppVersionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 应用版本
 */
@Tag(name = "应用版本", description = "应用版本")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminAppVersionController extends BaseController<AppVersionService, AppVersionEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields(APP_VERSION_ENTITY.NAME, APP_VERSION_ENTITY.VERSION)
            .fieldEq(APP_VERSION_ENTITY.STATUS, APP_VERSION_ENTITY.TYPE));
    }
}