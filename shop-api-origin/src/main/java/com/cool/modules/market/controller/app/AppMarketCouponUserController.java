package com.cool.modules.market.controller.app;

import static com.cool.modules.market.entity.table.MarketCouponInfoEntityTableDef.MARKET_COUPON_INFO_ENTITY;
import static com.cool.modules.market.entity.table.MarketCouponUserEntityTableDef.MARKET_COUPON_USER_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.NoRepeatSubmit;
import com.cool.core.base.BaseController;
import com.cool.core.enums.QueryModeEnum;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.market.entity.MarketCouponUserEntity;
import com.cool.modules.market.service.MarketCouponInfoService;
import com.cool.modules.market.service.MarketCouponUserService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 优惠券用户
 */
@Tag(name = "优惠券用户", description = "优惠券用户")
@CoolRestController(api = {"page", "list", "info"})
@RequiredArgsConstructor
public class AppMarketCouponUserController extends BaseController<MarketCouponUserService, MarketCouponUserEntity> {
    private final MarketCouponInfoService marketCouponInfoService;
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
            createOp()
                .select(MARKET_COUPON_INFO_ENTITY.ALL_COLUMNS,
                    MARKET_COUPON_USER_ENTITY.STATUS.as("useStatus"))
                .queryWrapper(
                    QueryWrapper.create()
                        .from(MARKET_COUPON_USER_ENTITY)
                        .leftJoin(MARKET_COUPON_INFO_ENTITY)
                        .on(MARKET_COUPON_USER_ENTITY.COUPON_ID.eq(MARKET_COUPON_INFO_ENTITY.ID))
                        .and(MARKET_COUPON_USER_ENTITY.USER_ID.eq(
                            CoolSecurityUtil.getCurrentUserId())))
                .queryModeEnum(QueryModeEnum.CUSTOM)
                .transform(o -> {
                    Map map = (Map) o;
                    Object json = map.get(MARKET_COUPON_INFO_ENTITY.CONDITION.getName());
                    if (ObjUtil.isNotEmpty(json)) {
                        map.put(MARKET_COUPON_INFO_ENTITY.CONDITION.getName(), JSONUtil.toBean(json.toString(), Map.class));
                    }
                }));
    }

    @Operation(summary = "'领取优惠券'")
    @PostMapping("/receive")
    @NoRepeatSubmit
    public R receive(@RequestAttribute() JSONObject requestParams) {
        Long couponId = requestParams.get("couponId", Long.class);
        return R.ok(marketCouponInfoService.receive(couponId, CoolSecurityUtil.getCurrentUserId()));
    }
}