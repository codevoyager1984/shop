package com.cool.modules.market.service;

import com.cool.core.base.BaseService;
import com.cool.modules.market.entity.MarketCouponInfoEntity;
import com.cool.modules.order.entity.OrderInfoEntity;

/**
 * 优惠券信息
 */
public interface MarketCouponInfoService extends BaseService<MarketCouponInfoEntity> {

    /**
     * 检查优惠券是否满足条件，满足的话使用并设置
     */
    void checkAndUse(Long couponId, OrderInfoEntity orderInfoEntity);

    /**
     * 领取优惠券
     */
    boolean receive(Long couponId, Long userId);
}
