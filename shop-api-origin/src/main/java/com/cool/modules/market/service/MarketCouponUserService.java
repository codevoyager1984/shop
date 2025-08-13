package com.cool.modules.market.service;

import com.cool.core.base.BaseService;
import com.cool.modules.market.entity.MarketCouponUserEntity;

/**
 * 优惠券用户
 */
public interface MarketCouponUserService extends BaseService<MarketCouponUserEntity> {

    /**
     * 检查优惠券是否可用
     */
    MarketCouponUserEntity check(Long couponId, Long userId);
    /**
     * 检查优惠券是否存在
     */
    boolean checkExist(Long couponId, Long userId);
    /**
     * 使用优惠券
     */
    void use(Long couponId, Long userId);
}
