package com.cool.modules.cs.controller.admin;

import static com.cool.modules.cs.entity.table.CsSessionEntityTableDef.CS_SESSION_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.cs.entity.CsSessionEntity;
import com.cool.modules.cs.service.CsSessionService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 客服会话
 */
@Tag(name = "客服会话", description = "客服会话")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminCsSessionController extends BaseController<CsSessionService, CsSessionEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
            createOp().select(CS_SESSION_ENTITY.ALL_COLUMNS,
                USER_INFO_ENTITY.NICK_NAME,
                USER_INFO_ENTITY.AVATAR_URL).queryWrapper(QueryWrapper.create()
                .from(CS_SESSION_ENTITY)
                .leftJoin(USER_INFO_ENTITY)
                .on(CS_SESSION_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID))));
    }
}