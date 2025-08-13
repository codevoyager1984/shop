package com.cool.modules.goods.controller.app;


import static com.cool.modules.goods.entity.table.GoodsCommentEntityTableDef.GOODS_COMMENT_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.annotation.TokenIgnore;
import com.cool.core.base.BaseController;
import com.cool.core.exception.CoolPreconditions;
import com.cool.core.request.R;
import com.cool.modules.goods.entity.GoodsCommentEntity;
import com.cool.modules.goods.service.GoodsCommentService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 商品评价
 */
@Tag(name = "商品评价", description = "商品评价")
@CoolRestController(api = {"page"})
@TokenIgnore(value = {"page"})
public class AppGoodsCommentController extends BaseController<GoodsCommentService, GoodsCommentEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().select(GOODS_COMMENT_ENTITY.ALL_COLUMNS, USER_INFO_ENTITY.NICK_NAME, USER_INFO_ENTITY.AVATAR_URL).fieldEq(GOODS_COMMENT_ENTITY.GOODS_ID, GOODS_COMMENT_ENTITY.ORDER_ID)
            .queryWrapper(QueryWrapper.create().from(GOODS_COMMENT_ENTITY).leftJoin(
                USER_INFO_ENTITY).on(GOODS_COMMENT_ENTITY.USER_ID.eq(USER_INFO_ENTITY.ID))));
    }

    @Operation(summary = "提交评论", description = "提交评论")
    @PostMapping("/submit")
    protected R submit(@RequestAttribute JSONObject requestParams) {
        JSONObject object = requestParams.get("data", JSONObject.class);
        CoolPreconditions.checkEmpty(object);

        GoodsCommentEntity goodsCommentEntity = object
            .toBean(GoodsCommentEntity.class);
        CoolPreconditions.checkEmpty(goodsCommentEntity);
        Long orderId = requestParams.get("orderId", Long.class);
        CoolPreconditions.checkEmpty(orderId);
        return R.ok(this.service.submit(goodsCommentEntity, orderId));
    }
}