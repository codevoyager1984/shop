package com.cool.modules.goods.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格
 */
@Getter
@Setter
@Table(value = "goods_spec", comment = "规格")
public class GoodsSpecEntity extends BaseEntity<GoodsSpecEntity> {

    @ColumnDefine(comment = "商品ID", notNull = true)
    private Long goodsId;

    @ColumnDefine(comment = "名称", notNull = true)
    private String name;

    @ColumnDefine(comment = "价格", decimalLength = 2, notNull = true)
    private BigDecimal price;

    @ColumnDefine(comment = "库存", defaultValue = "0", notNull = true)
    private Integer stock;

    @ColumnDefine(comment = "排序", defaultValue = "0", notNull = true)
    private Integer sortNum;

    @ColumnDefine(comment = "图片", type = "json")
    private String[] images;
}
