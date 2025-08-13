package com.cool.modules.goods.service;

import com.cool.core.base.BaseService;
import com.cool.modules.goods.entity.GoodsCommentEntity;

/**
 * 商品评价
 */
public interface GoodsCommentService extends BaseService<GoodsCommentEntity> {

    Object submit(GoodsCommentEntity goodsCommentEntity, Long orderId);
}
