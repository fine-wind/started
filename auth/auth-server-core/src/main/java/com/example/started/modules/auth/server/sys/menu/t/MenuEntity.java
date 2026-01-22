package com.example.started.modules.auth.server.sys.menu.t;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.started.dynamic.datasource.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单
 *
 * @since 1.0.0
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class MenuEntity extends BaseEntity {

    /**
     * pid
     */
    private Long pid;

    private Integer type;
    /**
     * icon
     */
    private String icon;
    /**
     * 标题
     */
    private String title;
    /**
     * path
     */
    private String path;
    /**
     * 用户状态
     * 0：不显示
     * 1：正常使用
     */
    private Integer state;
    /**
     * 排序
     */
    private Integer sort;
}
