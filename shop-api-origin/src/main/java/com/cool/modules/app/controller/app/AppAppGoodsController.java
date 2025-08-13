package com.cool.modules.app.controller.app;

import static com.cool.modules.app.entity.table.AppGoodsEntityTableDef.APP_GOODS_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.app.entity.AppGoodsEntity;
import com.cool.modules.app.service.AppGoodsService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 套餐
 */
@Tag(name = "套餐", description = "套餐")
@CoolRestController(api = {"list"})
public class AppAppGoodsController extends BaseController<AppGoodsService, AppGoodsEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().queryWrapper(
            QueryWrapper.create().and(APP_GOODS_ENTITY.STATUS.eq(1))
                .orderBy(APP_GOODS_ENTITY.SORT, true)));
    }
}