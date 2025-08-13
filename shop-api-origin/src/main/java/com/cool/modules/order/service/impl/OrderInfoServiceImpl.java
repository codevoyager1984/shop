package com.cool.modules.order.service.impl;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.cache.CoolCache;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.leaf.IDGenService;
import com.cool.core.util.CoolPluginInvokers;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.core.util.MappingAlgorithm;
import com.cool.modules.base.service.sys.BaseSysParamService;
import com.cool.modules.goods.entity.GoodsSpecEntity;
import com.cool.modules.goods.service.GoodsSpecService;
import com.cool.modules.market.entity.MarketCouponUserEntity;
import com.cool.modules.market.service.MarketCouponInfoService;
import com.cool.modules.order.entity.OrderGoodsEntity;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.entity.OrderInfoEntity.Logistics;
import com.cool.modules.order.entity.OrderInfoEntity.Refund;
import com.cool.modules.order.enums.ActionEnum;
import com.cool.modules.order.enums.OrderStatusEnum;
import com.cool.modules.order.mapper.OrderInfoMapper;
import com.cool.modules.order.queue.QueueProducer;
import com.cool.modules.order.service.OrderGoodsService;
import com.cool.modules.order.service.OrderInfoService;
import com.cool.modules.user.entity.UserAddressEntity;
import com.cool.modules.user.proxy.WxProxy;
import com.cool.modules.user.service.UserAddressService;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request.Amount;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.update.UpdateChain;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单信息
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfoMapper, OrderInfoEntity> implements OrderInfoService {

    private final UserAddressService userAddressService;

    private final GoodsSpecService goodsSpecService;

    private final OrderGoodsService orderGoodsService;

    private final MarketCouponInfoService marketCouponInfoService;

    private final BaseSysParamService baseSysParamService;

    private final QueueProducer queueProducer;

    private final IDGenService idGenService;
    
    private final CoolCache coolCache;

    private final WxProxy wxProxy;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object create(JSONObject data) {
        JSONObject requestParams = (JSONObject) data.get("data");
        // 地址id
        Long addressId = requestParams.get("addressId", Long.class);
        CoolPreconditions.checkEmpty(addressId);
        UserAddressEntity userAddressEntity = userAddressService.getById(addressId);
        CoolPreconditions.checkEmpty(userAddressEntity);

        OrderInfoEntity orderInfoEntity = requestParams.toBean(OrderInfoEntity.class);
        orderInfoEntity.setUserId(CoolSecurityUtil.getCurrentUserId());
        CoolPreconditions.checkEmpty(orderInfoEntity.getGoodsList());

        CoolPreconditions.checkEmpty(orderInfoEntity.getTitle());
        orderInfoEntity.setAddress(userAddressEntity);
        // 检查库存
        checkStock(orderInfoEntity.getGoodsList());
        orderInfoEntity.setPrice(orderGoodsService.getTotalPrice(orderInfoEntity.getGoodsList()));
        // 优惠券id
        Long couponId = requestParams.get("couponId", Long.class);
        // 使用优惠券
        if (ObjUtil.isNotEmpty(couponId)) {
            marketCouponInfoService.checkAndUse(couponId, orderInfoEntity);
        }
        orderInfoEntity.setId(idGenService.next("orderId"));
        orderInfoEntity.setOrderNum(generateOrderNum(orderInfoEntity.getId()));
        this.mapper.insertSelectiveWithPk(orderInfoEntity);
        // 保存订单商品
        orderInfoEntity.getGoodsList().forEach(o -> o.setOrderId(orderInfoEntity.getId()));
        orderGoodsService.saveBatch(orderInfoEntity.getGoodsList());
        // 更新商品库存
        orderGoodsService.updateStock(orderInfoEntity.getGoodsList(), "sub");
        String orderTimeoutStr = baseSysParamService.dataByKey("orderTimeout");
        long orderTimeout = ObjUtil.isEmpty(orderTimeoutStr) ? 120 : Long.parseLong(orderTimeoutStr);
        queueProducer.enqueueDelayedMessage(orderInfoEntity, ActionEnum.TIMEOUT, orderTimeout * 60);
        return orderInfoEntity;
    }

    @Override
    public void changeStatus(Long orderId, Integer status) {
        UpdateChain.of(OrderInfoEntity.class)
            .set(OrderInfoEntity::getStatus, status)
            .eq(OrderInfoEntity::getId, orderId).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object close(Long userId, Long orderId, String remark) {
        OrderInfoEntity orderInfoEntity = mapper.selectOneWithRelationsById(orderId);
        CoolPreconditions.checkEmpty(orderInfoEntity, "订单不存在");
        CoolPreconditions.check(ObjUtil.isNotEmpty(userId) &&
            ObjUtil.notEqual(orderInfoEntity.getUserId(), userId), "非法操作");
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getStatus(), 0), "订单状态不允许关闭");
        // 退回优惠券
        if (ObjUtil.isNotEmpty(orderInfoEntity.getDiscountSource())
            && orderInfoEntity.getDiscountPrice().doubleValue() > 0) {
            if (ObjUtil.equals(orderInfoEntity.getDiscountSource().getType(), 0)) {
                MarketCouponUserEntity marketCouponUserEntity = new MarketCouponUserEntity();
                marketCouponUserEntity.setId(orderInfoEntity.getDiscountSource().getObjectId());
                marketCouponUserEntity.setStatus(0);
                marketCouponUserEntity.updateById();
            }
        }
        orderInfoEntity.setStatus(OrderStatusEnum.CLOSED.getCode());
        orderInfoEntity.setCloseRemark(remark);
        this.update(orderInfoEntity);
        // 释放库存
        orderGoodsService.updateStock(orderInfoEntity.getGoodsList(), "add");
        return true;
    }

    @Override
    public Object refund(Long orderId, String reason) {
        OrderInfoEntity orderInfoEntity = getOrderInfoEntity(orderId);
        CoolPreconditions.check(!Arrays.asList(1, 2).contains(orderInfoEntity.getStatus()), "订单状态不允许退款");
        orderInfoEntity.setStatus(OrderStatusEnum.REFUND_IN_PROGRESS.getCode());
        Refund refund = new Refund();
        refund.setAmount(orderInfoEntity.getPrice().subtract(orderInfoEntity.getDiscountPrice()));
        refund.setReason(reason);
        refund.setStatus(0);
        refund.setApplyTime(new Date());
        refund.setReason(reason);
        refund.setOrderNum(orderInfoEntity.getOrderNum().replace("U", "R"));
        orderInfoEntity.setRefund(refund);
        orderInfoEntity.setRefundApplyTime(refund.getApplyTime());
        orderInfoEntity.setRefundStatus(refund.getStatus());
        return orderInfoEntity.updateById();
    }

    /**
     * 获取当前用户订单
     */
    private OrderInfoEntity getOrderInfoEntity(Long orderId) {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        OrderInfoEntity orderInfoEntity = this.getById(orderId);
        CoolPreconditions.checkEmpty(orderInfoEntity, "订单不存在");
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getUserId(), userId), "非法操作");
        return orderInfoEntity;
    }

    @Override
    public Object confirm(Long orderId) {
        OrderInfoEntity orderInfoEntity = getOrderInfoEntity(orderId);
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getStatus(), 2), "订单状态不允许确认收货");
        orderInfoEntity.setStatus(OrderStatusEnum.AWAITING_REVIEW.getCode());
        return orderInfoEntity.updateById();
    }

    @Override
    public Object logistics(Long orderId) {
        OrderInfoEntity orderInfoEntity = getOrderInfoEntity(orderId);
        if (ObjUtil.isEmpty(orderInfoEntity.getLogistics())
            || ObjUtil.isEmpty(orderInfoEntity.getLogistics().getNum())) {
            return null;
        }
        // 物流单号
        String num = orderInfoEntity.getLogistics().getNum();
        String cacheKey = "logistics:" + orderId + ":" + num;
        // 获取物流信息
        return coolCache.get(cacheKey, Duration.ofMinutes(30),
            () -> {
                Object invoke = CoolPluginInvokers.invoke("wuliu", "query", num, "");
                if (ObjUtil.isEmpty(invoke)) {
                    return null;
                }
                String result = (String) invoke;
                return JSONUtil.parseObj(result).get("result");
            });
    }

    @Override
    public Object userCount() {
        List<Row> rowList = this.mapper.selectRowsByQuery(
            QueryWrapper.create().select(ORDER_INFO_ENTITY.STATUS.getName(), "count(1) as cnt")
                .eq(OrderInfoEntity::getUserId, CoolSecurityUtil.getCurrentUserId())
                .groupBy(OrderInfoEntity::getStatus));
        Map<String, Integer> map = new HashMap<>();
        rowList.forEach(row -> map.put(OrderStatusEnum.fromCode(row.getInt(ORDER_INFO_ENTITY.STATUS.getName())).getDescription(),
            row.getInt("cnt")));

        return map;
    }

    @Override
    public OrderInfoEntity getByIdForAppUser(Long orderId) {
        return this.mapper.selectOneWithRelationsByQuery(QueryWrapper.create()
            .eq(OrderInfoEntity::getUserId, CoolSecurityUtil.getCurrentUserId())
            .eq(OrderInfoEntity::getId, orderId));
    }

    /**
     * 自动确认收货
     */
    @Override
    public void autoConfirm(Long orderId) {
        OrderInfoEntity orderInfoEntity = getById(orderId);
        if (ObjUtil.isEmpty(orderInfoEntity)) {
            log.warn("无该订单 {}", orderId);
            return;
        }
        if (ObjUtil.notEqual(orderInfoEntity.getStatus(), OrderStatusEnum.AWAITING_RECEIPT.getCode())) {
            log.warn("该订单 {} 当前状态为 {}, 不能自动确认收货", orderId, OrderStatusEnum.fromCode(orderInfoEntity.getStatus()).getDescription());
            return;
        }
        orderInfoEntity.setStatus(OrderStatusEnum.AWAITING_REVIEW.getCode());
        orderInfoEntity.updateById();
    }

    @Override
    public OrderInfoEntity getByOrderNum(String orderNum) {
        return mapper.selectOneByQuery(QueryWrapper.create().eq(OrderInfoEntity::getOrderNum, orderNum));
    }

    @Override
    public Object deliver(Long orderId, Logistics logistics) {
        OrderInfoEntity orderInfoEntity = getById(orderId);
        CoolPreconditions.checkEmpty(orderInfoEntity, "订单不存在");
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getStatus(), 1), "订单状态不允许发货");
        orderInfoEntity.setStatus(OrderStatusEnum.AWAITING_RECEIPT.getCode());
        orderInfoEntity.setLogistics(logistics);
        return orderInfoEntity.updateById();
    }

    @Override
    public void refundHandle(Long orderId, Integer action, String refuseReason, BigDecimal amount) {
        OrderInfoEntity orderInfoEntity = getById(orderId);
        CoolPreconditions.checkEmpty(orderInfoEntity, "订单不存在");
        CoolPreconditions.check(ObjUtil.notEqual(orderInfoEntity.getStatus(), OrderStatusEnum.REFUND_IN_PROGRESS.getCode())
                || ObjUtil.isEmpty(orderInfoEntity.getRefund()),
            "订单状态不允许退款处理");
        // 拒绝退款
        if (ObjUtil.equals(action, 0)) {
            orderInfoEntity.setStatus(OrderStatusEnum.TRANSACTION_COMPLETE.getCode());
            Refund refund = orderInfoEntity.getRefund();
            refund.setStatus(2);
            orderInfoEntity.setRefundStatus(refund.getStatus());
            refund.setRefuseReason(refuseReason);
            orderInfoEntity.updateById();
            return;
        }
        CoolPreconditions.checkEmpty(amount, "退款金额不能为空");
        // 同意退款
        CoolPreconditions.check(amount.doubleValue() > orderInfoEntity.getPrice().doubleValue(), "退款金额不能大于订单金额");
        WxPayService wxPayService = wxProxy.getWxPayService();
        // 执行退款操作
        WxPayRefundV3Request wxPayRefundV3Request = new WxPayRefundV3Request();
        wxPayRefundV3Request.setOutTradeNo(orderInfoEntity.getOrderNum());
        Amount refundAmount = new Amount();
        //设置币种信息
        refundAmount.setCurrency(WxPayConstants.CurrencyType.CNY);
        // 设置金额
        refundAmount.setRefund(amount.multiply(new BigDecimal(100)).intValue());
        refundAmount.setTotal(orderInfoEntity.getPrice().subtract(orderInfoEntity.getDiscountPrice()).multiply(new BigDecimal(100)).intValue());
        wxPayRefundV3Request.setAmount(refundAmount);
        wxPayRefundV3Request.setReason(orderInfoEntity.getRefund().getReason());
        try {
            WxPayRefundV3Result wxPayRefundV3Result = wxPayService.refundV3(wxPayRefundV3Request);
            CoolPreconditions.check(ObjUtil.equals(wxPayRefundV3Result.getStatus(), "CLOSED"), "退款关闭");
            CoolPreconditions.check(ObjUtil.equals(wxPayRefundV3Result.getStatus(), "ABNORMAL"), "退款异常");
        } catch (WxPayException e) {
            CoolPreconditions.alwaysThrow("退款失败", e);
        }
        orderInfoEntity.setStatus(OrderStatusEnum.REFUNDED.getCode());
        Refund refund = orderInfoEntity.getRefund();
        refund.setStatus(1);
        refund.setRealAmount(amount);
        refund.setTime(new Date());
        orderInfoEntity.setRefundStatus(refund.getStatus());
        orderInfoEntity.updateById();
    }

    /**
     * 生成订单号
     */
    private String generateOrderNum(Long orderId) {
        return "U" + DateUtil.format(new Date(), "YYMMDDHHmm") + MappingAlgorithm.encrypt(orderId);
    }

    /**
     * 检查库存
     */
    private void checkStock(List<OrderGoodsEntity> goodsList) {
        List<Long> goodsSpecIdList = goodsList.stream().map(o -> o.getSpec().getId()).toList();
        List<GoodsSpecEntity> goodsSpecEntities = goodsSpecService.listByIds(goodsSpecIdList);
        goodsSpecEntities.forEach(spec -> {
            goodsList.stream()
                .filter(g -> ObjUtil.equals(g.getSpec().getId(), spec.getId())).findFirst()
                .ifPresent(orderGoodsEntity -> {
                    CoolPreconditions.check(orderGoodsEntity.getCount() > spec.getStock(),
                        "商品[{}]，库存不足", orderGoodsEntity.getGoodsInfo().getTitle());
                    orderGoodsEntity.setPrice(spec.getPrice());
                });
        });
    }
}