package com.cool.modules.count.service;


import static com.cool.modules.goods.entity.table.GoodsInfoEntityTableDef.GOODS_INFO_ENTITY;
import static com.cool.modules.goods.entity.table.GoodsTypeEntityTableDef.GOODS_TYPE_ENTITY;
import static com.cool.modules.order.entity.table.OrderGoodsEntityTableDef.ORDER_GOODS_ENTITY;
import static com.mybatisflex.core.query.QueryMethods.sum;

import cn.hutool.core.util.ObjUtil;
import com.cool.modules.order.mapper.OrderGoodsMapper;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountGoodsService {
    private final OrderGoodsMapper orderGoodsMapper;

    /**
    * 销售排行
    * @param type 类型 count-数量 amount-金额
    * @param limit 条数
    */
    public Object rank(String type, int limit) {
        return orderGoodsMapper.selectListByQueryAs(QueryWrapper.create()
            .from(QueryWrapper.create()
                .select(ORDER_GOODS_ENTITY.GOODS_ID.as("goodId"), GOODS_INFO_ENTITY.TITLE, GOODS_INFO_ENTITY.MAIN_PIC.as("mainPic"),
                    ObjUtil.equal(type, "count") ? sum(ORDER_GOODS_ENTITY.COUNT).as("total") : sum(ORDER_GOODS_ENTITY.PRICE.multiply(ORDER_GOODS_ENTITY.COUNT)).as("total"))
                .from(ORDER_GOODS_ENTITY)
            .leftJoin(GOODS_INFO_ENTITY).on(ORDER_GOODS_ENTITY.GOODS_ID.eq(GOODS_INFO_ENTITY.ID))
            .groupBy(ORDER_GOODS_ENTITY.GOODS_ID)
            .orderBy("total", false)).as("a").limit(limit), Map.class);
    }

    /**
     * 分类统计
     * @param type 类型 count-数量 amount-金额
     */
    public Object category(String type) {
        return orderGoodsMapper.selectListByQueryAs(QueryWrapper.create()
            .select(GOODS_INFO_ENTITY.TYPE_ID.as("typeId"), GOODS_TYPE_ENTITY.NAME.as("typeName"),
                ObjUtil.equal(type, "count") ? sum(ORDER_GOODS_ENTITY.COUNT).as("total") : sum(ORDER_GOODS_ENTITY.PRICE.multiply(ORDER_GOODS_ENTITY.COUNT)).as("total"))
            .from(ORDER_GOODS_ENTITY)
            .leftJoin(GOODS_INFO_ENTITY).on(ORDER_GOODS_ENTITY.GOODS_ID.eq(GOODS_INFO_ENTITY.ID))
            .leftJoin(GOODS_TYPE_ENTITY).on(GOODS_INFO_ENTITY.TYPE_ID.eq(GOODS_TYPE_ENTITY.ID))
            .groupBy(GOODS_INFO_ENTITY.TYPE_ID), Map.class);
    }
}
