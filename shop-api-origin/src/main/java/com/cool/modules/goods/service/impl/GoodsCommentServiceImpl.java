package com.cool.modules.goods.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.goods.entity.GoodsCommentEntity;
import com.cool.modules.goods.mapper.GoodsCommentMapper;
import com.cool.modules.goods.service.GoodsCommentService;
import com.cool.modules.order.entity.OrderGoodsEntity;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderGoodsService;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 商品评价
 */
@Service
@RequiredArgsConstructor
public class GoodsCommentServiceImpl extends BaseServiceImpl<GoodsCommentMapper, GoodsCommentEntity> implements GoodsCommentService {
    private final OrderInfoService orderInfoService;

    private final OrderGoodsService orderGoodsService;
    @Override
    public Object submit(GoodsCommentEntity goodsCommentEntity, Long orderId) {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        OrderInfoEntity orderInfoEntity = orderInfoService.getById(orderId);
        CoolPreconditions.checkEmpty(orderInfoEntity);
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getUserId(), userId), "无权限");
        CoolPreconditions.check(!Arrays.asList(3, 4).contains(orderInfoEntity.getStatus()), "不是可评价的订单状态");

        OrderGoodsEntity orderGoodsEntity = orderGoodsService.getOne(
            QueryWrapper.create().eq(OrderGoodsEntity::getGoodsId, goodsCommentEntity.getGoodsId())
                .eq(OrderGoodsEntity::getOrderId, orderId));
        CoolPreconditions.checkEmpty(orderGoodsEntity, "商品不存在");
        CoolPreconditions.check(ObjUtil.equals(orderGoodsEntity.getIsComment(), 1), "已评价过，不能重复评价");
        // 更新订单商品状态
        orderGoodsEntity.setIsComment(1);
        orderGoodsEntity.updateById();
        // 订单状态改为交易完成
        if (ObjUtil.equals(orderInfoEntity.getStatus(), 3)) {
            orderInfoService.changeStatus(orderId, 4);
        }
        goodsCommentEntity.setUserId(userId);
        return goodsCommentEntity.save();
    }
}