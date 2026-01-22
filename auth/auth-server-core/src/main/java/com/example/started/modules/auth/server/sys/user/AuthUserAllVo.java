package com.example.started.modules.auth.server.sys.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 用户实体
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class AuthUserAllVo {

    /**
     * 登录名
     */
    private Long id;

    /**
     * 登录名
     */
    private String username;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * @see AuthUserEntity#getStatus()
     */
    private Integer status;

    public AuthUserAllVo(AuthUserEntity e) {
        BeanUtils.copyProperties(e, this);
    }
}
