package com.cool.modules.count.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.request.R;
import com.cool.modules.count.service.CountGoodsService;
import com.cool.modules.count.service.CountOrderService;
import com.cool.modules.count.service.CountUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 首页统计
 */
@CoolRestController
@RequiredArgsConstructor
public class AdminCountHomeController {

    private final CountUserService countUserService;

    private final CountOrderService countOrderService;

    private final CountGoodsService countGoodsService;

    @Operation(summary = "用户概况", description = "用户概况")
    @PostMapping("/userSummary")
    protected R userSummary(@RequestAttribute() JSONObject requestParams) {
        return R.ok(countUserService.summary());
    }

    @Operation(summary = "用户图表", description = "用户图表")
    @PostMapping("/userChart")
    protected R userChart(@RequestAttribute() JSONObject requestParams) {
        Integer dayCount = requestParams.get("dayCount", Integer.class);
        if (dayCount == null) {
            dayCount = 30;
        }
        return R.ok(countUserService.chart(dayCount));
    }

    @Operation(summary = "订单概况", description = "订单概况")
    @PostMapping("/orderSummary")
    protected R orderSummary(@RequestAttribute() JSONObject requestParams) {
        return R.ok(countOrderService.summary(requestParams.get("type", String.class)));
    }


    @Operation(summary = "订单图表", description = "订单图表")
    @PostMapping("/orderChart")
    protected R orderChart(@RequestAttribute() JSONObject requestParams) {
        Integer dayCount = requestParams.get("dayCount", Integer.class);
        if (dayCount == null) {
            dayCount = 30;
        }
        return R.ok(countOrderService.chart(dayCount, requestParams.get("type", String.class)));
    }

    @Operation(summary = "goodsRank", description = "商品排行")
    @PostMapping("/goodsRank")
    protected R goodsRank(@RequestAttribute() JSONObject requestParams) {
        Integer limit = requestParams.get("limit", Integer.class);
        if (limit == null) {
            limit = 10;
        }
        return R.ok(countGoodsService.rank(requestParams.get("type", String.class), limit));
    }

    @Operation(summary = "商品分类", description = "商品分类")
    @PostMapping("/goodsCategory")
    protected R goodsCategory(@RequestAttribute() JSONObject requestParams) {
        return R.ok(countGoodsService.category(requestParams.get("type", String.class)));
    }
}
