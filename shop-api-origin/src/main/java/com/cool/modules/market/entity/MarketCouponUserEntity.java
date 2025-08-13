package com.cool.modules.market.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券用户
 */
@Getter
@Setter
@Table(value = "market_coupon_user", comment = "优惠券用户")
public class MarketCouponUserEntity extends BaseEntity<MarketCouponUserEntity> {
    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;
    @Index
    @ColumnDefine(comment = "优惠券ID", notNull = true)
    private Long couponId;

    @ColumnDefine(comment = "状态 0-未使用 1-已使用", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "使用时间")
    private Date useTime;
}
