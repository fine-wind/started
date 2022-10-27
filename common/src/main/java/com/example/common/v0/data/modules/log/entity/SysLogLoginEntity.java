package com.example.common.v0.data.modules.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v1.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_login")
public class SysLogLoginEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户操作   0：用户登录   1：用户退出   2：访问主页面
     */
    private Integer operation;
    /**
     * 状态  0：失败    1：成功    2：账号已锁定
     */
    private Integer status;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 用户名
     */
    private String creatorName;
    /**
     * 更新者
     */
    @TableField(exist = false)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(exist = false)
    private Date updateDate;

}
