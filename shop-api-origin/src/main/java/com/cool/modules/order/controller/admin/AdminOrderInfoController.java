package com.cool.modules.order.controller.admin;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.enums.QueryModeEnum;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.request.CrudOption;
import com.cool.core.request.R;
import com.cool.modules.count.service.CountOrderService;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.entity.OrderInfoEntity.Logistics;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 订单信息
 */
@Tag(name = "订单信息", description = "订单信息")
@CoolRestController(api = {"update", "page", "info"})
@RequiredArgsConstructor
public class AdminOrderInfoController extends BaseController<OrderInfoService, OrderInfoEntity> {

    private final CountOrderService countOrderService;

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        Date startTime = requestParams.get("startTime", Date.class);
        Date endTime = requestParams.get("endTime", Date.class);
        Date refundApplyStartTime = requestParams.get("refundApplyStartTime", Date.class);
        setPageOption(createOp().queryModeEnum(QueryModeEnum.ENTITY_WITH_RELATIONS)
            .fieldEq(ORDER_INFO_ENTITY.PAY_TYPE, ORDER_INFO_ENTITY.STATUS, ORDER_INFO_ENTITY.REFUND_STATUS)
            .keyWordLikeFields(ORDER_INFO_ENTITY.TITLE, ORDER_INFO_ENTITY.ORDER_NUM, USER_INFO_ENTITY.NICK_NAME)
            .select(ORDER_INFO_ENTITY.ALL_COLUMNS, USER_INFO_ENTITY.NICK_NAME, USER_INFO_ENTITY.AVATAR_URL)
            .keyWordLikeFields(ORDER_INFO_ENTITY.TITLE, ORDER_INFO_ENTITY.ORDER_NUM, USER_INFO_ENTITY.NICK_NAME)
            .queryWrapper(QueryWrapper.create().from(ORDER_INFO_ENTITY).leftJoin(USER_INFO_ENTITY)
                .on(ORDER_INFO_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID))
                .ge(OrderInfoEntity::getCreateTime, startTime, ObjUtil.isNotEmpty(startTime))
                .le(OrderInfoEntity::getCreateTime, endTime, ObjUtil.isNotEmpty(endTime))
                .ge(OrderInfoEntity::getRefundApplyTime, refundApplyStartTime, ObjUtil.isNotEmpty(refundApplyStartTime))
                .le(OrderInfoEntity::getRefundApplyTime, refundApplyStartTime, ObjUtil.isNotEmpty(refundApplyStartTime))));
    }

    /**
     * 分页查询
     *
     * @param requestParams 请求参数
     */
    @Operation(summary = "分页", description = "分页查询多个信息")
    @PostMapping("/page")
    protected R page(@RequestAttribute() JSONObject requestParams,
        @RequestAttribute(COOL_PAGE_OP) CrudOption<OrderInfoEntity> option) {
        R result = super.page(requestParams, option);
        Map<String, Object> o = (Map<String, Object>) result.get("data");
        o.putAll(countOrderService.countTotal(option.getQueryWrapper()));
        return result;
    }

    @Operation(summary = "退款处理")
    @GetMapping("/refundHandle")
    protected R refundHandle(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        // 0-拒绝 1-同意
        Integer action = requestParams.get("action", Integer.class);
        CoolPreconditions.checkEmpty(action);
        // 拒绝原因
        String refuseReason = requestParams.get("refuseReason", String.class);
        // 退款金额
        BigDecimal amount = requestParams.get("amount", BigDecimal.class);
        this.service.refundHandle(orderId, action, refuseReason, amount);
        return R.ok();
    }

    @Operation(summary = "物流信息")
    @GetMapping("/logistics")
    protected R logistics(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(this.service.logistics(orderId));
    }

    @Operation(summary = "发货")
    @PostMapping("/deliver")
    protected R deliver(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        Logistics logistics = requestParams.get("logistics", Logistics.class);
        CoolPreconditions.checkEmpty(orderId);
        CoolPreconditions.checkEmpty(logistics);
        return R.ok(this.service.deliver(orderId, logistics));
    }
}