package com.cool.modules.goods.entity;
import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品类型
 */
@Getter
@Setter
@Table(value = "goods_type", comment = "商品类型")
public class GoodsTypeEntity extends BaseEntity<GoodsTypeEntity> {

    @ColumnDefine(comment = "名称", notNull = true)
    private String name;

    @ColumnDefine(comment = "父ID")
    private Integer parentId;

    @ColumnDefine(comment = "排序", defaultValue = "0")
    private Integer sortNum;

    @ColumnDefine(comment = "图片")
    private String pic;

    @ColumnDefine(comment = "状态 0-禁用 1-启用", defaultValue = "1")
    private Integer status;
}
