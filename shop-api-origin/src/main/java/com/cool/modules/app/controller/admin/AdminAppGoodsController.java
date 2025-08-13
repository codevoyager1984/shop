package com.cool.modules.app.controller.admin;

import static com.cool.modules.app.entity.table.AppGoodsEntityTableDef.APP_GOODS_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.app.entity.AppGoodsEntity;
import com.cool.modules.app.service.AppGoodsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 套餐
 */
@Tag(name = "套餐", description = "套餐")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminAppGoodsController extends BaseController<AppGoodsService, AppGoodsEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields(APP_GOODS_ENTITY.TITLE)
            .fieldEq(APP_GOODS_ENTITY.STATUS, APP_GOODS_ENTITY.TYPE));
    }
}