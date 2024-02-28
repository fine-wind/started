package com.example.started.auth.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 资源管理
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resources")
public class SysResourcesEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long pid;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 类型
     *
     * @see Constant.RESOURCES
     */
    private Integer type;
    /**
     * 菜单URL
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
     */
    private String permissions;

    /**
     * 状态
     *
     * @see Constant.Status
     */
    private Integer subpage;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    private Integer l;
    private Integer r;
    /**
     * 上级菜单名称
     */
    @TableField(exist = false)
    private String parentName;

}
