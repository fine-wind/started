package com.example.started.auth.client.user;

import com.example.common.v0.constant.Constant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 用户
 */
public class SecurityUser {

    /**
     * @return 获取用户登录名
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return "null";
        }

        Object username = authentication.getPrincipal();

        if (username instanceof SecurityUserDetails) {
            username = ((SecurityUserDetails) username).getUsername();
        }
        return String.valueOf(username);
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return getUserName();
    }

    public static SecurityUserDetails me() {
        return null;
    }

    /**
     * 获取用户ID
     */
    public static boolean superAdmin() {
        return Constant.Status.SUCCESS.equals(Constant.Status.SUCCESS);
    }

    public static Long getDeptId() {
        return null;
    }
}
