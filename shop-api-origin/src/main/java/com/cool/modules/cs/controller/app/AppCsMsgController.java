package com.cool.modules.cs.controller.app;

import static com.cool.modules.base.entity.sys.table.BaseSysUserEntityTableDef.BASE_SYS_USER_ENTITY;
import static com.cool.modules.cs.entity.table.CsMsgEntityTableDef.CS_MSG_ENTITY;
import static com.cool.modules.cs.entity.table.CsSessionEntityTableDef.CS_SESSION_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.cs.entity.CsMsgEntity;
import com.cool.modules.cs.service.CsMsgService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 客服消息
 */
@Tag(name = "客服消息", description = "客服消息")
@CoolRestController(api = {"page"})
public class AppCsMsgController extends BaseController<CsMsgService, CsMsgEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
            createOp().select(CS_MSG_ENTITY.ALL_COLUMNS,
                USER_INFO_ENTITY.NICK_NAME,
                USER_INFO_ENTITY.AVATAR_URL,
                BASE_SYS_USER_ENTITY.NAME.as("adminUserName"),
                BASE_SYS_USER_ENTITY.HEAD_IMG.as("adminUserHeadImg")).queryWrapper(QueryWrapper.create()
                .from(CS_MSG_ENTITY)
                .leftJoin(USER_INFO_ENTITY)
                .on(CS_MSG_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID)
                    .and(CS_MSG_ENTITY.TYPE.eq(0)))
                .leftJoin(BASE_SYS_USER_ENTITY)
                .on(CS_MSG_ENTITY.USER_ID.eq(BASE_SYS_USER_ENTITY.ID).and(CS_MSG_ENTITY.TYPE.eq(1)))
                .leftJoin(CS_SESSION_ENTITY)
                .on(CS_MSG_ENTITY.SESSION_ID.eq(CS_SESSION_ENTITY.ID))
                .and(CS_MSG_ENTITY.USER_ID.eq(CoolSecurityUtil.getCurrentUserId()))));
    }

    @Operation(summary = "未读消息数", description = "未读消息数")
    @GetMapping("/unreadCount")
    protected R unreadCount() {
        return R.ok(this.service.unreadCount(CoolSecurityUtil.getCurrentUserId(), 1));
    }

    @Operation(summary = "未读消息数", description = "标记已读")
    @PostMapping("/read")
    protected R read(@RequestAttribute JSONObject requestParams) {
        List<Long> msgIds = requestParams.get("msgIds", List.class);
        return R.ok(this.service.read(msgIds));
    }
}