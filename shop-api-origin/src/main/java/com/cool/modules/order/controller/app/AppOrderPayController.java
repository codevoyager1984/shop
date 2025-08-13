package com.cool.modules.order.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.request.R;
import com.cool.modules.order.service.OrderPayService;
import com.cool.modules.user.proxy.WxProxy;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result.JsapiResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单信息
 */
@Tag(name = "订单信息", description = "订单信息")
@CoolRestController
@RequiredArgsConstructor
@Slf4j
public class AppOrderPayController {

    private final OrderPayService orderPayService;

    private final WxProxy wxProxy;

    @Operation(summary = "支付回调通知处理")
    @PostMapping("/wxNotify")
    public String wxNotify(@RequestBody String xmlData) {
        WxPayService wxPayService = wxProxy.getWxPayService();
        try {
            // 解析微信支付的回调数据
            WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            String orderNum = notifyResult.getOutTradeNo(); // 商户订单号
            // 检查支付结果
            if ("SUCCESS".equals(notifyResult.getResultCode())) {
                // 支付成功的逻辑处理
                log.info("微信支付成功，订单号: {}", orderNum);
                orderPayService.paySuccess(orderNum, 1);
                return WxPayNotifyResponse.success("success"); // 返回给微信支付处理结果
            } else {
                log.error("微信支付失败，订单号: {}", orderNum);
                return WxPayNotifyResponse.fail("fail");
            }
        } catch (Exception e) {
            log.error("微信支付回调处理异常: {}", e.getMessage(), e);
            return WxPayNotifyResponse.fail("fail");
        }
    }

    @Operation(summary = "微信小程序支付")
    @PostMapping("/wxMiniPay")
    public R wxMiniPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        JsapiResult orderResult  = (JsapiResult) orderPayService.wxMiniPay(orderId);
        Map<String, String> map = Maps.newHashMap();
        map.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("nonceStr", orderResult.getNonceStr());
        map.put("package", orderResult.getPackageValue());
        map.put("signType", "MD5");
        WxPayService wxPayService = wxProxy.getWxPayService();
        map.put("paySign", SignUtils.createSign(map, "MD5", wxPayService.getConfig().getMchKey(), null));
        return R.ok(Map.of("data", map));
    }

    @Operation(summary = "微信小程序支付")
    @PostMapping("/wxMpPay")
    public R wxMpPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.wxMpPay(orderId)));
    }

    @Operation(summary = "微信APP支付")
    @PostMapping("/wxAppPay")
    public R wxAppPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.wxAppPay(orderId)));
    }
}