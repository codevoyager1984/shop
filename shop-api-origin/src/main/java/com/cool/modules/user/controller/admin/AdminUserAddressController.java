package com.cool.modules.user.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.user.entity.UserAddressEntity;
import com.cool.modules.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户模块-收货地址
 */
@Tag(name = "用户模块-收货地址", description = "用户模块-收货地址")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminUserAddressController extends BaseController<UserAddressService, UserAddressEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}