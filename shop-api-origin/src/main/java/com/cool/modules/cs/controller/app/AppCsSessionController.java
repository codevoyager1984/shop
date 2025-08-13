package com.cool.modules.cs.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.cs.entity.CsSessionEntity;
import com.cool.modules.cs.service.CsSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 客服会话
 */
@Tag(name = "客服会话", description = "客服会话")
@CoolRestController
public class AppCsSessionController extends BaseController<CsSessionService, CsSessionEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

    @Operation(summary = "会话详情", description = "会话详情")
    @GetMapping("/detail")
    protected R detail() {
        return R.ok(this.service.detail(CoolSecurityUtil.getCurrentUserId()));
    }

    @Operation(summary = "创建会话", description = "创建会话")
    @PostMapping("/create")
    protected R create() {
        return R.ok(this.service.create(CoolSecurityUtil.getCurrentUserId()));
    }
}