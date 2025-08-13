package com.cool.modules.order.service;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseService;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.entity.OrderInfoEntity.Logistics;
import java.math.BigDecimal;

/**
 * 订单信息
 */
public interface OrderInfoService extends BaseService<OrderInfoEntity> {

    /**
     * 创建订单
     * @param requestParams
     * @return
     */
    Object create(JSONObject requestParams);

    /**
     * 修改订单状态
     * @param orderId
     * @param status
     */
    void changeStatus(Long orderId, Integer status);

    /**
     * 关闭
     */
    Object close(Long userId, Long orderId, String remark);

    /**
     * 退款
     * @param orderId
     * @param reason
     * @return
     */
    Object refund(Long orderId, String reason);

    /**
     * 确认收货
     * @param orderId
     * @return
     */
    Object confirm(Long orderId);

    /**
     * 物流信息
     * @param orderId
     * @return
     */
    Object logistics(Long orderId);

    /**
     * 用户订单统计
     * @return
     */
    Object userCount();

    /**
     * 获取app user的订单详情信息
     */
    OrderInfoEntity getByIdForAppUser(Long orderId);

    /**
     * 自动确认收货
     */
    void autoConfirm(Long orderId);

    /**
     * 通过订单号获取订单信息
     */
    OrderInfoEntity getByOrderNum(String orderNum);

    /**
     * 发货
     */
    Object deliver(Long orderId, Logistics logistics);

    /**
     * 退款
     */
    void refundHandle(Long orderId, Integer action, String refuseReason, BigDecimal amount);
}
