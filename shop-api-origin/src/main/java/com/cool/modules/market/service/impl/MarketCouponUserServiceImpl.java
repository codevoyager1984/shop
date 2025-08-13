package com.cool.modules.market.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.market.entity.MarketCouponUserEntity;
import com.cool.modules.market.mapper.MarketCouponUserMapper;
import com.cool.modules.market.service.MarketCouponUserService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 优惠券用户
 */
@Service
public class MarketCouponUserServiceImpl extends BaseServiceImpl<MarketCouponUserMapper, MarketCouponUserEntity> implements MarketCouponUserService {

    /**
     * 检查优惠券是否可用
     */
    @Override
    public MarketCouponUserEntity check(Long couponId, Long userId) {
        MarketCouponUserEntity couponUserEntity = getOne(
            QueryWrapper.create().eq(MarketCouponUserEntity::getCouponId, couponId)
                .eq(MarketCouponUserEntity::getUserId, userId).eq(MarketCouponUserEntity::getStatus, 0));
        CoolPreconditions.checkEmpty(couponUserEntity, "优惠券未领取或已使用");
        return couponUserEntity;
    }

    @Override
    public boolean checkExist(Long couponId, Long userId) {
        return count(QueryWrapper.create().eq(MarketCouponUserEntity::getCouponId, couponId)
            .eq(MarketCouponUserEntity::getUserId, userId)) > 0;
    }

    /**
     * 使用优惠券
     */
    @Override
    public void use(Long couponId, Long userId) {

    }
}