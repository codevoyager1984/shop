package com.cool.modules.cs.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 客服连接
 */
@Getter
@Setter
@Table(value = "cs_conn", comment = "客服连接")
public class CsConnEntity extends BaseEntity<CsConnEntity> {
    @Index
    @ColumnDefine(comment = "用户ID", notNull = true)
    private Long userId;

    @ColumnDefine(comment = "连接ID", notNull = true)
    private String connId;

    @ColumnDefine(comment = "类型 0-客户 1-后台", defaultValue = "0")
    private Integer type;
}
