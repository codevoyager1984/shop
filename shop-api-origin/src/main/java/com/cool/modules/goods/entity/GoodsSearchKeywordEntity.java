package com.cool.modules.goods.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 搜索关键词
 */
@Getter
@Setter
@Table(value = "goods_search_keyword", comment = "搜索关键词")
public class GoodsSearchKeywordEntity extends BaseEntity<GoodsSearchKeywordEntity> {

    @ColumnDefine(comment = "名称", notNull = true)
    private String name;

    @ColumnDefine(comment = "排序", defaultValue = "0")
    private Integer sortNum;
}
