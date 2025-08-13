package com.cool.modules.goods.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品信息
 */
@Getter
@Setter
@Table(value = "goods_info", comment = "商品信息")
public class GoodsInfoEntity extends BaseEntity<GoodsInfoEntity> {

    @Index
    @ColumnDefine(comment = "类型ID", notNull = true)
    private Integer typeId;

    @Index
    @ColumnDefine(comment = "标题", notNull = true)
    private String title;

    @ColumnDefine(comment = "副标题", type = "text")
    private String subTitle;

    @ColumnDefine(comment = "主图", type = "text", notNull = true)
    private String mainPic;

    @ColumnDefine(comment = "图片", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> pics;

    @ColumnDefine(comment = "价格", type = "decimal", decimalLength = 2, notNull = true)
    private BigDecimal price;

    @ColumnDefine(comment = "已售", defaultValue = "0")
    private Integer sold;

    @ColumnDefine(comment = "详情", type = "text")
    private String content;

    @Index
    @ColumnDefine(comment = "状态 0-下架 1-上架", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "排序", defaultValue = "0")
    private Integer sortNum;

    // 规格，非表字段
    @Column(ignore = true)
    private List<GoodsSpecEntity> specs;
}
