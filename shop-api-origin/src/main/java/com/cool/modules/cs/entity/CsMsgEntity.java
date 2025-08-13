package com.cool.modules.cs.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 客服消息
 */
@Getter
@Setter
@Table(value = "cs_msg", comment = "客服消息")
public class CsMsgEntity extends BaseEntity<CsMsgEntity> {

    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;

    @Index
    @ColumnDefine(comment = "会话ID", notNull = true)
    private Long sessionId;

    @ColumnDefine(comment = "消息内容", type = "json", notNull = true)
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private MessageContent content;

    @ColumnDefine(comment = "类型 0-反馈 1-回复", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "状态 0-未读 1-已读", defaultValue = "0")
    private Integer status;

    @Setter
    @Getter
    public static class MessageContent {
        /**
         *     TEXT("text"),
         *     IMAGE("image"),
         *     VOICE("voice"),
         *     VIDEO("video"),
         *     FILE("file"),
         *     LINK("link"),
         *     LOCATION("location"),
         *     EMOJI("emoji");
         */
        private String type;

        private String data;
    }

    @Column(ignore = true)
    private String nickName;
    @Column(ignore = true)
    private String avatarUrl;
    @Column(ignore = true)
    private String adminUserName;
    @Column(ignore = true)
    private String adminUserHeadImg;
}
