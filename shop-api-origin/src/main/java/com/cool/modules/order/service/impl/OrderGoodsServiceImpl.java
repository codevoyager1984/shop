package com.cool.modules.order.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.goods.service.GoodsSpecService;
import com.cool.modules.order.entity.OrderGoodsEntity;
import com.cool.modules.order.mapper.OrderGoodsMapper;
import com.cool.modules.order.service.OrderGoodsService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单商品
 */
@Service
@RequiredArgsConstructor
public class OrderGoodsServiceImpl extends BaseServiceImpl<OrderGoodsMapper, OrderGoodsEntity> implements OrderGoodsService {
    private final GoodsSpecService goodsSpecService;
    @Override
    public BigDecimal getTotalPrice(List<OrderGoodsEntity> goodsList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderGoodsEntity goods : goodsList) {
            BigDecimal count = BigDecimal.valueOf(goods.getCount());
            totalPrice = totalPrice.add(goods.getPrice().multiply(count));
        }
        return totalPrice;
    }

    @Override
    public void updateStock(List<OrderGoodsEntity> goodsList, String type) {
        goodsList.forEach(o -> goodsSpecService.updateStock(o.getSpec().getId(), ObjUtil.equals(type, "add") ? o.getCount() : -o.getCount()));
    }
}