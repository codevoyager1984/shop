package com.cool.modules.app.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 意见反馈
 */
@Getter
@Setter
@Table(value = "app_feedback", comment = "意见反馈")
public class AppFeedbackEntity extends BaseEntity<AppFeedbackEntity> {

    @ColumnDefine(comment = "用户ID", notNull = true)
    private Integer userId;

    @ColumnDefine(comment = "联系方式", notNull = true)
    private String contact;

    @ColumnDefine(comment = "类型", notNull = true)
    private Integer type;

    @ColumnDefine(comment = "内容", type = "text", notNull = true)
    private String content;

    @ColumnDefine(comment = "图片", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> images;

    @ColumnDefine(comment = "状态 0-未处理 1-已处理", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "处理人ID", type = "integer")
    private Long handlerId;

    @ColumnDefine(comment = "备注", type = "text")
    private String remark;
}
