package com.cool.modules.app.controller.app;

import static com.cool.modules.app.entity.table.AppFeedbackEntityTableDef.APP_FEEDBACK_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.app.entity.AppFeedbackEntity;
import com.cool.modules.app.service.AppFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 意见反馈
 */
@Tag(name = "意见反馈", description = "意见反馈")
@CoolRestController(api = {"page", "info"})
public class AppAppFeedbackController extends BaseController<AppFeedbackService, AppFeedbackEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().fieldEq(APP_FEEDBACK_ENTITY.USER_ID, APP_FEEDBACK_ENTITY.TYPE));
    }

    @Operation(summary = "提交意见反馈", description = "提交意见反馈")
    @PostMapping("/submit")
    protected R submit(@RequestAttribute JSONObject requestParams) {
        return R.ok(this.service.save(requestParams.toBean(AppFeedbackEntity.class)));
    }
}