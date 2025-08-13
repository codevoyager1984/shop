package com.cool.modules.goods.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.goods.entity.GoodsCommentEntity;
import com.cool.modules.goods.service.GoodsCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 商品评价
 */
@Tag(name = "商品评价", description = "商品评价")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGoodsCommentController extends BaseController<GoodsCommentService, GoodsCommentEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}