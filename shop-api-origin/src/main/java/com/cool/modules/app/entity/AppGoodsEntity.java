package com.cool.modules.app.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 套餐
 */
@Getter
@Setter
@Table(value = "app_goods", comment = "套餐")
public class AppGoodsEntity extends BaseEntity<AppGoodsEntity> {

    @ColumnDefine(comment = "标题", notNull = true)
    private String title;

    @ColumnDefine(comment = "价格", type = "decimal", decimalLength = 2, notNull = true)
    private BigDecimal price;

    @ColumnDefine(comment = "原价", type = "decimal", decimalLength = 2, notNull = true)
    private BigDecimal originalPrice;

    @ColumnDefine(comment = "描述", type = "text")
    private String description;

    @ColumnDefine(comment = "状态 0-禁用 1-启用", defaultValue = "1")
    private Integer status;

    @ColumnDefine(comment = "排序", defaultValue = "0")
    private Integer sort;

    @ColumnDefine(comment = "类型 0-天 1-月 2-年 3-永久", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "时长", defaultValue = "1")
    private Integer duration;

    @ColumnDefine(comment = "标签")
    private String tag;

    @ColumnDefine(comment = "标签颜色", defaultValue = "#26A7FD")
    private String tagColor;
}
