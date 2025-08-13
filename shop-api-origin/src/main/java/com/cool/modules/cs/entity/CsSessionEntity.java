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
 * 客服会话
 */
@Getter
@Setter
@Table(value = "cs_session", comment = "客服会话")
public class CsSessionEntity extends BaseEntity<CsSessionEntity> {

    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;

    @ColumnDefine(comment = "最后一条消息", type = "json")
    @Column(typeHandler = Fastjson2TypeHandler.class)
    private CsMsgEntity lastMsg;

    @ColumnDefine(comment = "客服未读消息数", defaultValue = "0")
    private Long adminUnreadCount;

    @Column(ignore = true)
    private String nickName;
    @Column(ignore = true)
    private String avatarUrl;
}
