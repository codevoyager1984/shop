package com.cool.modules.market.controller.app;

import static com.cool.modules.market.entity.table.MarketCouponInfoEntityTableDef.MARKET_COUPON_INFO_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.market.entity.MarketCouponInfoEntity;
import com.cool.modules.market.service.MarketCouponInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 优惠券信息
 */
@Tag(name = "优惠券信息", description = "优惠券信息")
@CoolRestController(api = {"page"})
public class AppMarketCouponInfoController extends BaseController<MarketCouponInfoService, MarketCouponInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        Date date = new Date();
        setPageOption(
        createOp()
            .queryWrapper(
                QueryWrapper.create()
                    .from(MARKET_COUPON_INFO_ENTITY)
                    .and(MARKET_COUPON_INFO_ENTITY.STATUS.eq(1))
                    .and(MARKET_COUPON_INFO_ENTITY.START_TIME.le(date))
                    .and(MARKET_COUPON_INFO_ENTITY.END_TIME.ge(date))));
    }
}