package com.cool.modules.order.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.order.entity.OrderGoodsEntity;
import com.cool.modules.order.service.OrderGoodsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 订单商品
 */
@Tag(name = "订单商品", description = "订单商品")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminOrderGoodsController extends BaseController<OrderGoodsService, OrderGoodsEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}