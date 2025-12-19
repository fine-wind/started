package com.example.started.modules.auth.server.sys.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthUserBo {

    /**
     * 用户操作   0：用户登录   1：用户退出   2：访问主页面
     */
    private Integer operation;
    /**
     * 状态  0：失败    1：成功
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

}
