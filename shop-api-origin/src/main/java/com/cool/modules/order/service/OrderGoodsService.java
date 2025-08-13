package com.cool.modules.order.service;

import com.cool.core.base.BaseService;
import com.cool.modules.order.entity.OrderGoodsEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品
 */
public interface OrderGoodsService extends BaseService<OrderGoodsEntity> {

    /**
     * 获得总价
     */
    BigDecimal getTotalPrice(List<OrderGoodsEntity> goodsList);

    /**
     * 更新库存
     */
    void updateStock(List<OrderGoodsEntity> goodsList, String type);
}
