package com.example.started.modules.auth.group;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.started.dynamic.datasource.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@TableName("auth_group")
@EqualsAndHashCode(callSuper = true)
public class AuthGroupEntity extends BaseEntity {

    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 用户状态
     * 0：未激活
     * 100：正常使用
     */
    private String status;
}
