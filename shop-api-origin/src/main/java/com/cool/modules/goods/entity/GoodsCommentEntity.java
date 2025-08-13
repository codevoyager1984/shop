package com.cool.modules.goods.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品评价
 */
@Getter
@Setter
@Table(value = "goods_comment", comment = "商品评价")
public class GoodsCommentEntity extends BaseEntity<GoodsCommentEntity> {
    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;

    @ColumnDefine(comment = "商品ID", notNull = true)
    private Long goodsId;
    @Index
    @ColumnDefine(comment = "订单ID", notNull = true)
    private Long orderId;

    @ColumnDefine(comment = "内容", notNull = true)
    private String content;

    @ColumnDefine(comment = "星数", defaultValue = "5", notNull = true)
    private Integer starCount;

    @ColumnDefine(comment = "图片", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> pics;

    @Column(ignore = true)
    private String nickName;
    @Column(ignore = true)
    private String avatarUrl;
}
