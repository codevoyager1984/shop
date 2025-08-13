package com.cool.modules.app.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 应用版本
 */
@Getter
@Setter
@Table(value = "app_version", comment = "应用版本")
public class AppVersionEntity extends BaseEntity<AppVersionEntity> {

    @ColumnDefine(comment = "名称", notNull = true)
    private String name;

    @ColumnDefine(comment = "版本号", length = 50, notNull = true)
    private String version;

    @ColumnDefine(comment = "类型", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "下载地址", notNull = true)
    private String url;

    @ColumnDefine(comment = "强制更新 0-否 1-是", defaultValue = "0")
    private Integer forceUpdate;

    @ColumnDefine(comment = "状态 0-禁用 1-启用", defaultValue = "1")
    private Integer status;

    @ColumnDefine(comment = "热更新 0-否 1-是", defaultValue = "0")
    private Integer hotUpdate;

    @ColumnDefine(comment = "描述", type = "text", notNull = true)
    private String description;
}
