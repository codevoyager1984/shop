package com.cool.modules.goods.controller.app;

import static com.cool.modules.goods.entity.table.GoodsInfoEntityTableDef.GOODS_INFO_ENTITY;
import static com.cool.modules.goods.entity.table.GoodsTypeEntityTableDef.GOODS_TYPE_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsInfoEntity;
import com.cool.modules.goods.service.GoodsInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 商品信息
 */
@Tag(name = "商品信息", description = "商品信息")
@CoolRestController(api = {"page","info"})
@TokenIgnore(value = {"page","info"})
public class AppGoodsInfoController extends BaseController<GoodsInfoService, GoodsInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        BigDecimal minPrice = requestParams.get("minPrice", BigDecimal.class);
        BigDecimal maxPrice = requestParams.get("maxPrice", BigDecimal.class);
        setPageOption(createOp().fieldEq(GOODS_INFO_ENTITY.TYPE_ID)
            .keyWordLikeFields(GOODS_INFO_ENTITY.TITLE)
            .queryWrapper(QueryWrapper.create().from(GOODS_INFO_ENTITY).leftJoin(
                    GOODS_TYPE_ENTITY).on(GOODS_INFO_ENTITY.TYPE_ID.eq(GOODS_TYPE_ENTITY.ID))
                .eq(GoodsInfoEntity::getStatus, 1)
                .gt(GoodsInfoEntity::getPrice, minPrice, ObjUtil.isNotEmpty(minPrice))
                .le(GoodsInfoEntity::getPrice, maxPrice, ObjUtil.isNotEmpty(maxPrice))));
    }
}