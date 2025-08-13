package com.cool.modules.order.service;

public interface OrderPayService {

    Object wxMiniPay(Long orderId);

    Object wxMpPay(Long orderId);

    Object wxAppPay(Long orderId);

    // 支付宝支付相关方法
    Object alipayWapPay(Long orderId);
    
    Object alipayAppPay(Long orderId);
    
    Object alipayPagePay(Long orderId);
    
    Object alipayMiniPay(Long orderId);

    void paySuccess(String orderId, int payType);
}
