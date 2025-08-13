package com.cool.modules.info.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.modules.info.entity.InfoBannerEntity;
import com.cool.modules.info.service.InfoBannerService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 轮播图
 */
@Tag(name = "轮播图", description = "轮播图")
@CoolRestController(api = {"list"})
@TokenIgnore(value = {"list"})
public class AppInfoBannerController extends BaseController<InfoBannerService, InfoBannerEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setListOption(createOp().queryWrapper(QueryWrapper.create().eq(InfoBannerEntity::getStatus, 1)));
    }
}
