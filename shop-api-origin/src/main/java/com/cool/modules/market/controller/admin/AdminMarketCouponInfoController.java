package com.cool.modules.market.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.market.entity.MarketCouponInfoEntity;
import com.cool.modules.market.service.MarketCouponInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 优惠券信息
 */
@Tag(name = "优惠券信息", description = "优惠券信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminMarketCouponInfoController extends BaseController<MarketCouponInfoService, MarketCouponInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}