package com.cool.modules.goods.controller.app;

import static com.cool.modules.goods.entity.table.GoodsSearchKeywordEntityTableDef.GOODS_SEARCH_KEYWORD_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsSearchKeywordEntity;
import com.cool.modules.goods.service.GoodsSearchKeywordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 搜索关键词
 */
@Tag(name = "搜索关键词", description = "搜索关键词")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AppGoodsSearchKeywordController extends BaseController<GoodsSearchKeywordService, GoodsSearchKeywordEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields(GOODS_SEARCH_KEYWORD_ENTITY.NAME));
    }
}