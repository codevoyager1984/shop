package com.cool.modules.base.controller.admin;

import com.cool.core.annotation.CoolRestController;
import com.cool.core.enums.UserTypeEnum;
import com.cool.core.eps.CoolEps;
import com.cool.core.request.R;
import com.cool.modules.base.dto.sys.BaseSysLoginDto;
import com.cool.modules.base.service.sys.BaseSysLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统开放接口，无需权限校验
 */
@RequiredArgsConstructor
@Tag(name = "系统开放", description = "系统开放")
@CoolRestController()
public class AdminBaseOpenController {

    final private BaseSysLoginService baseSysLoginService;

    final private CoolEps coolEps;

    @Operation(summary = "实体信息与路径", description = "系统所有的实体信息与路径，供前端自动生成代码与服务")
    @GetMapping("/eps")
    public R eps() {
        return R.ok(coolEps.getAdmin());
    }

    @Operation(summary = "获得网页内容的参数值")
    @GetMapping("/html")
    public R html() {
        return R.ok();
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R login(@RequestBody BaseSysLoginDto baseSysLoginDto) {
        return R.ok(baseSysLoginService.login(baseSysLoginDto));
    }

    @Operation(summary = "验证码")
    @GetMapping("/captcha")
    public R captcha(@Parameter(description = "类型：svg|base64") @RequestParam(defaultValue = "base64") String type,
        @Parameter(description = "宽度") @RequestParam(defaultValue = "150") Integer width,
        @Parameter(description = "高度") @RequestParam(defaultValue = "50") Integer height) {
        return R.ok(baseSysLoginService.captcha(UserTypeEnum.ADMIN, type, width, height));
    }

    @Operation(summary = "刷新token")
    @GetMapping("/refreshToken")
    public R refreshToken(String refreshToken) {
        return R.ok(baseSysLoginService.refreshToken(refreshToken));
    }
}
