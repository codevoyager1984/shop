package com.cool.modules.goods.controller.app;

import static com.cool.modules.goods.entity.table.GoodsSpecEntityTableDef.GOODS_SPEC_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsSpecEntity;
import com.cool.modules.goods.service.GoodsSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 规格
 */
@Tag(name = "规格", description = "规格")
@CoolRestController(api = {"list"})
@TokenIgnore(value = {"list"})
public class AppGoodsSpecController extends BaseController<GoodsSpecService, GoodsSpecEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setListOption(createOp().fieldEq(GOODS_SPEC_ENTITY.GOODS_ID));
    }
}