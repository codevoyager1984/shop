package com.cool.modules.market.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券信息
 */
@Getter
@Setter
@Table(value = "market_coupon_info", comment = "优惠券信息")
public class MarketCouponInfoEntity extends BaseEntity<MarketCouponInfoEntity> {

    @ColumnDefine(comment = "标题", notNull = true)
    private String title;

    @ColumnDefine(comment = "描述", notNull = true)
    private String description;

    @ColumnDefine(comment = "类型 0-满减", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "金额", type = "decimal", decimalLength = 2, notNull = true)
    private BigDecimal amount;

    @ColumnDefine(comment = "数量", notNull = true)
    private Integer num;

    @ColumnDefine(comment = "已领取", notNull = true)
    private Integer receivedNum;

    @ColumnDefine(comment = "开始时间", type = "datetime", notNull = true)
    private Date startTime;

    @ColumnDefine(comment = "结束时间", type = "datetime", notNull = true)
    private Date endTime;

    @ColumnDefine(comment = "状态 0-禁用 1-启用", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "条件", type = "json", notNull = true)
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private Condition condition;

    @Getter
    @Setter
    public static class Condition {
        // 满多少金额
        private BigDecimal fullAmount;
    }
}
