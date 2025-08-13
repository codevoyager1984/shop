package com.cool.modules.info.entity;
import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;

/**
 * 轮播图实体类
 */
@Getter
@Setter
@Table(value = "info_banner", comment = "轮播图")
public class InfoBannerEntity extends BaseEntity<InfoBannerEntity> {

    @ColumnDefine(comment = "描述")
    private String description;

    @ColumnDefine(comment = "跳转路径")
    private String path;

    @ColumnDefine(comment = "图片", notNull = true)
    private String pic;

    @ColumnDefine(comment = "排序", defaultValue = "0")
    private Integer sortNum;

    @ColumnDefine(comment = "状态 1:启用 2:禁用", defaultValue = "1")
    private Integer status;
}
