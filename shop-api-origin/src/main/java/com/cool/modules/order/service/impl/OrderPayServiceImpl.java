package com.cool.modules.order.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.base.service.sys.BaseSysParamService;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.enums.ActionEnum;
import com.cool.modules.order.enums.OrderStatusEnum;
import com.cool.modules.order.queue.QueueProducer;
import com.cool.modules.order.service.OrderInfoService;
import com.cool.modules.order.service.OrderPayService;
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
