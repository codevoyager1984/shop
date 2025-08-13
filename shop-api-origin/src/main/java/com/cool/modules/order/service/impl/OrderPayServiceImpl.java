package com.cool.modules.order.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.base.service.sys.BaseSysParamService;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.enums.ActionEnum;
import com.cool.modules.order.enums.OrderStatusEnum;
import com.cool.modules.order.queue.QueueProducer;
import com.cool.modules.order.service.OrderInfoService;
import com.cool.modules.order.service.OrderPayService;
import com.cool.modules.user.proxy.AlipayProxy;
import com.cool.modules.user.proxy.WxProxy;
import com.cool.modules.user.service.UserWxService;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPayServiceImpl implements OrderPayService {
    private final OrderInfoService orderInfoService;

    private final BaseSysParamService baseSysParamService;

    private final QueueProducer queueProducer;

    private final UserWxService userWxService;

    private final WxProxy wxProxy;
    
    private final AlipayProxy alipayProxy;

    /**
     * 微信小程序支付
     */
    @Override
    public Object wxMiniPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return wxJSAPI(orderInfo, 0);
    }

    /**
     * 微信公众号支付
     */
    @Override
    public Object wxMpPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return wxJSAPI(orderInfo, 1);
    }

    /**
     *
     * @param orderInfo
     * @param type 0-小程序 1-公众号 2-App
     */
    private Object wxJSAPI(OrderInfoEntity orderInfo, int type) {
        WxPayService wxPayService = wxProxy.getWxPayService();
        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        request.setOutTradeNo(orderInfo.getOrderNum());
        request.setNotifyUrl(wxPayService.getConfig().getNotifyUrl());
        request.setDescription("商品采购");

        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(userWxService.getOpenid(orderInfo.getUserId(), type));
        request.setPayer(payer);
        //构建金额信息
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        //设置币种信息
        amount.setCurrency(WxPayConstants.CurrencyType.CNY);
        // 设置金额
        amount.setTotal(orderInfo.getPrice().subtract(orderInfo.getDiscountPrice()).multiply(new BigDecimal(100)).intValue());
        request.setAmount(amount);
        try {
            return wxPayService.createOrderV3(TradeTypeEnum.JSAPI, request);
        } catch (Exception e) {
            CoolPreconditions.alwaysThrow("生成预支付单失败", e);
        }
        return null;
    }

    /**
     * 微信APP支付
     */
    @Override
    public Object wxAppPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        CoolPreconditions.alwaysThrow("暂不支持APP支付");
        return wxJSAPI(orderInfo, 2);
    }

    /**
     * 支付宝手机网站支付
     */
    @Override
    public Object alipayWapPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return createAlipayWapPay(orderInfo);
    }

    /**
     * 支付宝APP支付
     */
    @Override
    public Object alipayAppPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return createAlipayAppPay(orderInfo);
    }

    /**
     * 支付宝电脑网站支付
     */
    @Override
    public Object alipayPagePay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return createAlipayPagePay(orderInfo);
    }

    /**
     * 支付宝小程序支付
     */
    @Override
    public Object alipayMiniPay(Long orderId) {
        OrderInfoEntity orderInfo = getOrderInfo(orderId);
        return createAlipayMiniPay(orderInfo);
    }

    /**
     * 创建支付宝手机网站支付
     */
    private Object createAlipayWapPay(OrderInfoEntity orderInfo) {
        try {
            AlipayClient alipayClient = alipayProxy.getAlipayClient();
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(orderInfo.getOrderNum());
            model.setTotalAmount(orderInfo.getPrice().subtract(orderInfo.getDiscountPrice()).toString());
            model.setSubject("商品采购");
            model.setProductCode("QUICK_WAP_WAY");
            
            request.setBizModel(model);
            request.setNotifyUrl(getAlipayNotifyUrl());
            request.setReturnUrl(getAlipayReturnUrl());
            
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                CoolPreconditions.alwaysThrow("创建支付宝支付订单失败：" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            CoolPreconditions.alwaysThrow("支付宝支付异常", e);
        }
        return null;
    }

    /**
     * 创建支付宝APP支付
     */
    private Object createAlipayAppPay(OrderInfoEntity orderInfo) {
        try {
            AlipayClient alipayClient = alipayProxy.getAlipayClient();
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setOutTradeNo(orderInfo.getOrderNum());
            model.setTotalAmount(orderInfo.getPrice().subtract(orderInfo.getDiscountPrice()).toString());
            model.setSubject("商品采购");
            model.setProductCode("QUICK_MSECURITY_PAY");
            
            request.setBizModel(model);
            request.setNotifyUrl(getAlipayNotifyUrl());
            
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                CoolPreconditions.alwaysThrow("创建支付宝APP支付订单失败：" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            CoolPreconditions.alwaysThrow("支付宝APP支付异常", e);
        }
        return null;
    }

    /**
     * 创建支付宝电脑网站支付
     */
    private Object createAlipayPagePay(OrderInfoEntity orderInfo) {
        try {
            AlipayClient alipayClient = alipayProxy.getAlipayClient();
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(orderInfo.getOrderNum());
            model.setTotalAmount(orderInfo.getPrice().subtract(orderInfo.getDiscountPrice()).toString());
            model.setSubject("商品采购");
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            
            request.setBizModel(model);
            request.setNotifyUrl(getAlipayNotifyUrl());
            request.setReturnUrl(getAlipayReturnUrl());
            
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                CoolPreconditions.alwaysThrow("创建支付宝网站支付订单失败：" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            CoolPreconditions.alwaysThrow("支付宝网站支付异常", e);
        }
        return null;
    }

    /**
     * 创建支付宝小程序支付
     */
    private Object createAlipayMiniPay(OrderInfoEntity orderInfo) {
        try {
            AlipayClient alipayClient = alipayProxy.getAlipayClient();
            AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
            
            AlipayTradeCreateModel model = new AlipayTradeCreateModel();
            model.setOutTradeNo(orderInfo.getOrderNum());
            model.setTotalAmount(orderInfo.getPrice().subtract(orderInfo.getDiscountPrice()).toString());
            model.setSubject("商品采购");
            // 小程序支付需要买家的user_id，这里需要根据实际情况获取支付宝用户ID
            // model.setBuyerId("支付宝用户ID");
            
            request.setBizModel(model);
            request.setNotifyUrl(getAlipayNotifyUrl());
            
            AlipayTradeCreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                // 返回trade_no用于小程序调起支付
                return response.getTradeNo();
            } else {
                CoolPreconditions.alwaysThrow("创建支付宝小程序支付订单失败：" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            CoolPreconditions.alwaysThrow("支付宝小程序支付异常", e);
        }
        return null;
    }

    /**
     * 获取支付宝异步通知地址
     */
    private String getAlipayNotifyUrl() {
        // 这里需要根据实际部署的域名配置
        return baseSysParamService.dataByKey("alipayNotifyUrl");
    }

    /**
     * 获取支付宝同步返回地址
     */
    private String getAlipayReturnUrl() {
        // 这里需要根据实际部署的域名配置
        return baseSysParamService.dataByKey("alipayReturnUrl");
    }

    @Override
    public void paySuccess(String orderNum, int payType) {
        OrderInfoEntity orderInfoEntity = orderInfoService.getByOrderNum(orderNum);
        CoolPreconditions.check(ObjUtil.isEmpty(orderInfoEntity), "订单不存在");
        if (ObjUtil.equals(orderInfoEntity.getStatus(), OrderStatusEnum.AWAITING_PAYMENT.getCode())) {
            // 已支付更新订单状态
            orderInfoEntity.setStatus(OrderStatusEnum.AWAITING_DELIVERY.getCode());
            orderInfoEntity.setPayType(payType);
            orderInfoEntity.setPayTime(new Date());
            orderInfoEntity.updateById();
        }
        // 发送自动确认收货队列
        String orderConfirmStr = baseSysParamService.dataByKey("orderConfirm");
        int day = ObjUtil.isEmpty(orderConfirmStr) ? 15  : Integer.parseInt(orderConfirmStr);
        queueProducer.enqueueDelayedMessage(orderInfoEntity, ActionEnum.CONFIRM, (long) day * 60 * 60);
    }

    private OrderInfoEntity getOrderInfo(Long orderId) {
        OrderInfoEntity orderInfoEntity = orderInfoService.getByIdForAppUser(orderId);
        CoolPreconditions.check(ObjUtil.isEmpty(orderInfoEntity) ||
            ObjUtil.notEqual(orderInfoEntity.getStatus(), 0), "订单不存在或不是可以支付的状态");
        return orderInfoEntity;
    }
}
