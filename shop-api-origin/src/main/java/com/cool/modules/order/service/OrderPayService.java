package com.cool.modules.order.service;

public interface OrderPayService {

    Object wxMiniPay(Long orderId);

    Object wxMpPay(Long orderId);

    Object wxAppPay(Long orderId);

    void paySuccess(String orderId, int payType);
}
