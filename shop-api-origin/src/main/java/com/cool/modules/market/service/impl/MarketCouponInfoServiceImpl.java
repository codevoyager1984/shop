package com.cool.modules.market.service.impl;

import static com.cool.modules.market.entity.table.MarketCouponInfoEntityTableDef.MARKET_COUPON_INFO_ENTITY;

import cn.hutool.core.util.ObjUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.market.entity.MarketCouponInfoEntity;
import com.cool.modules.market.entity.MarketCouponUserEntity;
import com.cool.modules.market.mapper.MarketCouponInfoMapper;
import com.cool.modules.market.service.MarketCouponInfoService;
import com.cool.modules.market.service.MarketCouponUserService;
import com.cool.modules.order.entity.OrderGoodsEntity;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.entity.OrderInfoEntity.DiscountSource;
import com.mybatisflex.core.update.UpdateChain;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 优惠券信息
 */
@Service
@RequiredArgsConstructor
public class MarketCouponInfoServiceImpl extends BaseServiceImpl<MarketCouponInfoMapper, MarketCouponInfoEntity> implements MarketCouponInfoService {

    private final MarketCouponUserService marketCouponUserService;

    @Override
    public void checkAndUse(Long couponId, OrderInfoEntity orderInfoEntity) {
        // 检查优惠券状态
        MarketCouponInfoEntity couponInfo = check(couponId);
        // 判断用户
        marketCouponUserService.check(couponId,
            orderInfoEntity.getUserId());

        if (couponInfo.getType() == 0) {
            CoolPreconditions.check(orderInfoEntity.getPrice().compareTo(couponInfo.getCondition().getFullAmount()) < 0,
                "未满足优惠券使用条件");
            BigDecimal discountAmount = couponInfo.getAmount();
            orderInfoEntity.setDiscountPrice(discountAmount);

            DiscountSource discountSource = new DiscountSource();
            discountSource.setType(0);
            discountSource.setObjectId(couponId);
            discountSource.setInfo(couponInfo);
            orderInfoEntity.setDiscountSource(discountSource);

            if (orderInfoEntity.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                List<OrderGoodsEntity> goodsList = orderInfoEntity.getGoodsList();
                for (OrderGoodsEntity goods : goodsList) {
                    // 优惠金额 = 商品价格 / 订单总价 * 优惠金额 保留两位小数
                    BigDecimal orderPrice = orderInfoEntity.getPrice();
                    BigDecimal couponAmount = couponInfo.getAmount();
                    goods.setDiscountPrice(
                        goods.getPrice().divide(orderPrice, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(couponAmount));
                }
            }
            // 使用优惠券
            marketCouponUserService.use(couponId, orderInfoEntity.getUserId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean receive(Long couponId, Long userId) {
        // 检查优惠券
        check(couponId);
        // 检查用户是否已领取
        CoolPreconditions.check(marketCouponUserService.checkExist(couponId, userId),
            "已领取过该优惠券");
        MarketCouponUserEntity marketCouponUserEntity = new MarketCouponUserEntity();
        marketCouponUserEntity.setUserId(userId);
        marketCouponUserEntity.setCouponId(couponId);
        marketCouponUserService.save(marketCouponUserEntity);
        // 增加领取数量
        return UpdateChain.of(MarketCouponInfoEntity.class)
            .setRaw(MarketCouponInfoEntity::getReceivedNum, MARKET_COUPON_INFO_ENTITY.RECEIVED_NUM.getName() + "+1")
            .where(MarketCouponInfoEntity::getId).eq(couponId)
            .update();
    }

    /**
     * 检查优惠券状态
     */
    private MarketCouponInfoEntity check(Long couponId) {
        CoolPreconditions.checkEmpty(couponId, "优惠券不存在");
        MarketCouponInfoEntity couponInfo = this.getById(couponId);
        CoolPreconditions.checkEmpty(couponInfo, "优惠券不存在");
        // 判断数量
        CoolPreconditions.checkEmpty(couponInfo.getReceivedNum() >= couponInfo.getNum(),
            "优惠券已领完");
        // 判断时间
        Date date = new Date();
        CoolPreconditions.checkEmpty(date.before(couponInfo.getStartTime()) || date.after(couponInfo.getEndTime()),
            "优惠券未开始或已结束");
        // 判断状态
        CoolPreconditions.checkEmpty(ObjUtil.notEqual(couponInfo.getStatus(), 1),
            "优惠券未启用");
        return couponInfo;
    }
}