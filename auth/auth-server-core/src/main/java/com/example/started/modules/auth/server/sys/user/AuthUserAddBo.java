package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.constant.PageBo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class AuthUserAddBo {
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
    private Integer status;
}
