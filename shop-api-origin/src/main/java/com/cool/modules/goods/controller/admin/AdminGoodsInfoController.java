package com.cool.modules.goods.controller.admin;

import static com.cool.modules.goods.entity.table.GoodsInfoEntityTableDef.GOODS_INFO_ENTITY;
import static com.cool.modules.goods.entity.table.GoodsTypeEntityTableDef.GOODS_TYPE_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsInfoEntity;
import com.cool.modules.goods.service.GoodsInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 商品信息
 */
@Tag(name = "商品信息", description = "商品信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGoodsInfoController extends BaseController<GoodsInfoService, GoodsInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
            .select(GOODS_INFO_ENTITY.ALL_COLUMNS)
            .keyWordLikeFields(GOODS_INFO_ENTITY.TITLE, GOODS_INFO_ENTITY.SUB_TITLE)
            .fieldEq(GOODS_INFO_ENTITY.STATUS, GOODS_INFO_ENTITY.TYPE_ID)
            .queryWrapper(QueryWrapper.create().from(GOODS_INFO_ENTITY)
                .leftJoin(GOODS_TYPE_ENTITY)
                .on(GOODS_INFO_ENTITY.TYPE_ID.eq(GOODS_TYPE_ENTITY.ID))
                .orderBy(GOODS_INFO_ENTITY.SORT_NUM, false)));
    }
}