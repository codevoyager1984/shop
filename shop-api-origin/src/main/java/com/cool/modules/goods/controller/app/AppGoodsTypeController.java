package com.cool.modules.goods.controller.app;

import static com.cool.modules.goods.entity.table.GoodsTypeEntityTableDef.GOODS_TYPE_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsTypeEntity;
import com.cool.modules.goods.service.GoodsTypeService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 商品类型
 */
@Tag(name = "商品类型", description = "商品类型")
@CoolRestController(api = {"list"})
@TokenIgnore(value = {"list"})
public class AppGoodsTypeController extends BaseController<GoodsTypeService, GoodsTypeEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setListOption(createOp().keyWordLikeFields(GOODS_TYPE_ENTITY.NAME)
            .queryWrapper(QueryWrapper.create().and(GOODS_TYPE_ENTITY.STATUS.eq(1))
                .orderBy(GOODS_TYPE_ENTITY.SORT_NUM, false)));
    }
}