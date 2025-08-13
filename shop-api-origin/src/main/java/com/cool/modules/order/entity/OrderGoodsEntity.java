package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.cool.modules.goods.entity.GoodsInfoEntity;
import com.cool.modules.goods.entity.GoodsSpecEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(value = "order_goods", comment = "订单商品")
public class OrderGoodsEntity extends BaseEntity<OrderGoodsEntity> {

    @Index
    @ColumnDefine(comment = "订单ID", notNull = true)
    private Long orderId;

    @Index
    @ColumnDefine(comment = "商品ID", notNull = true)
    private Long goodsId;

    @ColumnDefine(comment = "价格", type = "decimal", decimalLength = 2, notNull = true)
    private BigDecimal price;

    @ColumnDefine(comment = "优惠金额", type = "decimal", decimalLength = 2, defaultValue = "0", notNull = true)
    private BigDecimal discountPrice;

    @ColumnDefine(comment = "数量", notNull = true)
    private Integer count;

    @ColumnDefine(comment = "其他信息", type = "text")
    private String remark;

    // 商品快照 随着时间的推移，商品信息会变，这边必须记录购买时的商品信息
    @ColumnDefine(comment = "商品信息", type = "json", notNull = true)
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private GoodsInfoEntity goodsInfo;

    @ColumnDefine(comment = "规格", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private GoodsSpecEntity spec;

    @ColumnDefine(comment = "是否评价 0-否 1-是", defaultValue = "0", notNull = true)
    private Integer isComment;
}
