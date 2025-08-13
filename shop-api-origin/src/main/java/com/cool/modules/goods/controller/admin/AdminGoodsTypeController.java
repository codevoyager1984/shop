package com.cool.modules.goods.controller.admin;

import static com.cool.modules.goods.entity.table.GoodsTypeEntityTableDef.GOODS_TYPE_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsTypeEntity;
import com.cool.modules.goods.service.GoodsTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 商品类型
 */
@Tag(name = "商品类型", description = "商品类型")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGoodsTypeController extends BaseController<GoodsTypeService, GoodsTypeEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields(GOODS_TYPE_ENTITY.NAME)
            .fieldEq(GOODS_TYPE_ENTITY.PARENT_ID));
    }
}