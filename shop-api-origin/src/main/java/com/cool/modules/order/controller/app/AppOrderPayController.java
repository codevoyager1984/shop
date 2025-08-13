package com.cool.modules.order.controller.app;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.request.R;
import com.cool.modules.order.service.OrderPayService;
import com.cool.modules.user.config.AlipayConfig;
import com.cool.modules.user.proxy.WxProxy;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result.JsapiResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    
    private final AlipayConfig alipayConfig;

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

    @Operation(summary = "支付宝手机网站支付")
    @PostMapping("/alipayWapPay")
    public R alipayWapPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.alipayWapPay(orderId)));
    }

    @Operation(summary = "支付宝APP支付")
    @PostMapping("/alipayAppPay")
    public R alipayAppPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.alipayAppPay(orderId)));
    }

    @Operation(summary = "支付宝电脑网站支付")
    @PostMapping("/alipayPagePay")
    public R alipayPagePay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.alipayPagePay(orderId)));
    }

    @Operation(summary = "支付宝小程序支付")
    @PostMapping("/alipayMiniPay")
    public R alipayMiniPay(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(Map.of("data", orderPayService.alipayMiniPay(orderId)));
    }

    @Operation(summary = "支付宝支付回调通知处理")
    @PostMapping("/alipayNotify")
    public String alipayNotify(HttpServletRequest request) {
        try {
            Map<String, String> params = Maps.newHashMap();
            Map<String, String[]> requestParams = request.getParameterMap();
            
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            
            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                params, 
                alipayConfig.getAlipayPublicKey(), 
                alipayConfig.getCharset(), 
                alipayConfig.getSignType()
            );
            
            if (signVerified) {
                String outTradeNo = params.get("out_trade_no");
                String tradeStatus = params.get("trade_status");
                
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    log.info("支付宝支付成功，订单号: {}", outTradeNo);
                    orderPayService.paySuccess(outTradeNo, 2);
                    return "success";
                } else {
                    log.warn("支付宝支付状态异常，订单号: {}, 状态: {}", outTradeNo, tradeStatus);
                    return "fail";
                }
            } else {
                log.error("支付宝回调签名验证失败");
                return "fail";
            }
        } catch (AlipayApiException e) {
            log.error("支付宝支付回调处理异常: {}", e.getMessage(), e);
            return "fail";
        }
    }
}