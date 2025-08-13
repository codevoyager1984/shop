package com.cool.modules.app.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.app.entity.AppVersionEntity;
import com.cool.modules.app.service.AppVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 应用版本
 */
@Tag(name = "应用版本", description = "应用版本")
@CoolRestController
public class AppAppVersionController extends BaseController<AppVersionService, AppVersionEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }

    @TokenIgnore
    @Operation(summary = "检查版本", description = "检查版本")
    @GetMapping("/check")
    protected R check(@RequestAttribute JSONObject requestParams) {
        String version = requestParams.get("version", String.class);
        Integer type = requestParams.get("type", Integer.class);
        return R.ok(this.service.check(version, type));
    }
}