package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.cool.modules.user.entity.UserAddressEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.autotable.annotation.Ignore;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.autotable.annotation.IndexField;
import com.tangzc.autotable.annotation.enums.IndexSortTypeEnum;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单信息实体类
 */
@Getter
@Setter
@Table(value = "order_info", comment = "订单信息")
@IndexField(field = "createTime", sort = IndexSortTypeEnum.ASC)
public class OrderInfoEntity extends BaseEntity<OrderInfoEntity> {

    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;

    @ColumnDefine(comment = "标题")
    private String title;

    @ColumnDefine(comment = "支付方式 0-待支付 1-微信 2-支付宝", defaultValue = "0", notNull = true)
    private Integer payType;

    @ColumnDefine(comment = "支付时间", type = "datetime")
    private Date payTime;

    @Index
    @ColumnDefine(comment = "订单号", notNull = true)
    private String orderNum;

    @ColumnDefine(comment = "状态 0-待付款 1-待发货 2-待收货 3-待评价 4-交易完成 5-退款中 6-已退款 7-已关闭", defaultValue = "0", notNull = true)
    private Integer status;

    @ColumnDefine(comment = "价格", decimalLength = 2, notNull = true)
    private BigDecimal price;

    @ColumnDefine(comment = "优惠金额", decimalLength = 2, defaultValue = "0")
    private BigDecimal discountPrice;

    @ColumnDefine(comment = "优惠来源", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private DiscountSource discountSource;

    @ColumnDefine(comment = "地址", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private UserAddressEntity address;

    @ColumnDefine(comment = "物流信息", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private Logistics logistics;

    @ColumnDefine(comment = "退款", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private Refund refund;

    @Index
    @ColumnDefine(comment = "退款状态")
    private Integer refundStatus;

    @Index
    @ColumnDefine(comment = "退款申请时间")
    private Date refundApplyTime;

    @ColumnDefine(comment = "备注")
    private String remark;

    @ColumnDefine(comment = "关闭备注")
    private String closeRemark;

    @ColumnDefine(comment = "已开票: 0-未开票 1-已开票", defaultValue = "0")
    private Integer invoice;

    @ColumnDefine(comment = "微信类型 0-小程序 1-公众号 2-App", defaultValue = "0")
    private Integer wxType;

    // 订单商品列表
    @Ignore
    @Column(ignore = true)
    @RelationOneToMany(selfField = "id", targetField = "orderId")
    private List<OrderGoodsEntity> goodsList;

    /**
     * 优惠来源
     */
    @Getter
    @Setter
    public static class DiscountSource {
        private Integer type; // 0-优惠券
        private Long objectId; // 对象ID
        private Object info; // 信息
    }

    /**
     * 物流信息
     */
    @Getter
    @Setter
    public static class Logistics {
        private String company; // 物流公司
        private String num; // 物流单号
    }

    /**
     * 退款信息
     */
    @Getter
    @Setter
    public static class Refund {
        private String orderNum; // 退款单号
        private BigDecimal amount; // 金额
        private BigDecimal realAmount; // 实际退款金额
        private Integer status; // 状态 0-申请中 1-已退款 2-拒绝
        private Date applyTime; // 申请时间
        private Date time; // 退款时间
        private String reason; // 退款原因
        private String refuseReason; // 拒绝原因
    }
}
