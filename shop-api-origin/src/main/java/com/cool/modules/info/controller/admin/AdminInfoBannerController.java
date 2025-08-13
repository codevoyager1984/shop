package com.cool.modules.info.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.info.entity.InfoBannerEntity;
import com.cool.modules.info.service.InfoBannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 轮播图
 */
@Tag(name = "轮播图", description = "轮播图")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminInfoBannerController extends BaseController<InfoBannerService, InfoBannerEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}