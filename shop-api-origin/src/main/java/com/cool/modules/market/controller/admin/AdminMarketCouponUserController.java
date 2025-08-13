package com.cool.modules.market.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.market.entity.MarketCouponUserEntity;
import com.cool.modules.market.service.MarketCouponUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 优惠券用户
 */
@Tag(name = "优惠券用户", description = "优惠券用户")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminMarketCouponUserController extends BaseController<MarketCouponUserService, MarketCouponUserEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}