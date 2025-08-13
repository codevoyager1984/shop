package com.cool.modules.app.controller.app;

import static com.cool.modules.app.entity.table.AppComplainEntityTableDef.APP_COMPLAIN_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.app.entity.AppComplainEntity;
import com.cool.modules.app.service.AppComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 举报投诉
 */
@Tag(name = "举报投诉", description = "举报投诉")
@CoolRestController(api = {"page", "info"})
public class AppAppComplainController extends BaseController<AppComplainService, AppComplainEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().fieldEq(APP_COMPLAIN_ENTITY.USER_ID, APP_COMPLAIN_ENTITY.TYPE));
    }
    @Operation(summary = "提交投诉举报", description = "提交投诉举报")
    @PostMapping("/submit")
    protected R submit(@RequestAttribute JSONObject requestParams) {
        return R.ok(this.service.save(requestParams.toBean(AppComplainEntity.class)));
    }
}