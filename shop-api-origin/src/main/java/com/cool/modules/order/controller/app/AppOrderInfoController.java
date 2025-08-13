package com.cool.modules.order.controller.app;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.NoRepeatSubmit;
import com.cool.core.base.BaseController;
import com.cool.core.enums.QueryModeEnum;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单信息
 */
@Tag(name = "订单信息", description = "订单信息")
@CoolRestController(api = {"update", "page", "info"})
public class AppOrderInfoController extends BaseController<OrderInfoService, OrderInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        JSONArray jsonArray = requestParams.get("status", JSONArray.class);
        setPageOption(createOp().queryModeEnum(QueryModeEnum.ENTITY_WITH_RELATIONS)
            .fieldEq().queryWrapper(
            QueryWrapper.create().and(ORDER_INFO_ENTITY.USER_ID.eq(
                CoolSecurityUtil.getCurrentUserId())
                .and(ORDER_INFO_ENTITY.STATUS.in(jsonArray, ObjUtil.isNotEmpty(jsonArray))))));
    }

    /**
     * 获取app user的订单详情信息
     *
     * @param id ID
     */
    @Operation(summary = "信息", description = "根据ID查询单个信息")
    @GetMapping("/info")
    protected R info(@RequestAttribute() JSONObject requestParams,
        @RequestParam() Long id) {
        return R.ok(service.getByIdForAppUser(id));
    }

    @Operation(summary = "创建订单", description = "创建订单")
    @PostMapping("/create")
    @NoRepeatSubmit
    protected R create(@RequestAttribute() JSONObject requestParams) {
        return R.ok(this.service.create(requestParams));
    }

    @Operation(summary = "取消订单", description = "取消订单")
    @PostMapping("/cancel")
    protected R cancel(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        String remark = requestParams.get("remark", String.class);
        CoolPreconditions.checkEmpty(orderId);
        CoolPreconditions.checkEmpty(remark);
        return R.ok(this.service.close(CoolSecurityUtil.getCurrentUserId(), orderId, remark));
    }

    @Operation(summary = "退款", description = "退款")
    @PostMapping("/refund")
    protected R refund(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        String reason = requestParams.get("reason", String.class);
        CoolPreconditions.checkEmpty(orderId);
        CoolPreconditions.checkEmpty(reason);
        return R.ok(this.service.refund(orderId, reason));
    }

    @Operation(summary = "确认收货", description = "确认收货")
    @GetMapping("/confirm")
    protected R confirm(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(this.service.confirm(orderId));
    }

    @Operation(summary = "物流信息", description = "物流信息")
    @GetMapping("/logistics")
    protected R logistics(@RequestAttribute() JSONObject requestParams) {
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(this.service.logistics(orderId));
    }

    @Operation(summary = "用户订单统计", description = "用户订单统计")
    @GetMapping("/userCount")
    protected R userCount() {
        return R.ok(this.service.userCount());
    }
}