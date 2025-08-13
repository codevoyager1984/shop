package com.cool.modules.count.service;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;
import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.sum;

import cn.hutool.core.util.ObjUtil;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.mapper.OrderInfoMapper;
import com.mybatisflex.core.query.QueryWrapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountOrderService {

    private final OrderInfoMapper orderInfoMapper;

    private final CountCommService countCommService;

    public Map countTotal(QueryWrapper queryWrapper){
        String sql = queryWrapper.toSQL();
        return Map.of("subData",orderInfoMapper.selectOneByQueryAs(QueryWrapper.create().select(count(ORDER_INFO_ENTITY.ID).as("totalCount"),
                sum(ORDER_INFO_ENTITY.PRICE).as("totalPrice"))
            .where(sql.contains("WHERE") ? sql.split("WHERE")[1].split("ORDER BY")[0] : ""), Map.class));
    }

    /**
     * 概况
     */
    public Map<String, Object> summary(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("today", createQuery(type, countCommService.getTimeRange("day")));
        map.put("week", createQuery(type, countCommService.getTimeRange("week")));
        map.put("month", createQuery(type, countCommService.getTimeRange("month")));
        map.put("year", createQuery(type, countCommService.getTimeRange("year")));
        map.put("total", createQuery(type, new HashMap<>()));
        return map;
    }

    private Object createQuery(String type, Map<String, String> timeRange) {
        if (ObjUtil.equals(type, "amount")) {
            BigDecimal bigDecimal = orderInfoMapper.selectOneByQueryAs(QueryWrapper.create()
                .select(sum(ORDER_INFO_ENTITY.PRICE))
                .ge(OrderInfoEntity::getCreateTime, timeRange.get("start"))
                .le(OrderInfoEntity::getCreateTime, timeRange.get("end")), BigDecimal.class);
            return ObjUtil.isEmpty(bigDecimal) ? 0 : bigDecimal.doubleValue();
        }
        return orderInfoMapper.selectCountByQuery(QueryWrapper.create()
            .ge(OrderInfoEntity::getCreateTime, timeRange.get("start"))
            .le(OrderInfoEntity::getCreateTime, timeRange.get("end")));
    }

  /**
   *
   * 图表
   * @param dayCount 最近多少天
   * @param type 类型 count-数量 amount-金额
   */
    public Map<String, Object> chart(int dayCount, String type) {
        if (ObjUtil.equals(type, "amount")) {
            return countCommService.chart(orderInfoMapper, dayCount, sum(ORDER_INFO_ENTITY.PRICE), ORDER_INFO_ENTITY.CREATE_TIME);
        }
        return countCommService.chart(orderInfoMapper, dayCount, count(ORDER_INFO_ENTITY.ID), ORDER_INFO_ENTITY.CREATE_TIME);
    }
}
