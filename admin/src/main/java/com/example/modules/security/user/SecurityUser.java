package com.example.modules.security.user;

import com.example.cache.redis.RedisUtils;
import com.example.cache.constant.CacheCommonKeys;
import com.example.common.v0.utils.SpringContextUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 用户
 */
public class SecurityUser {

    /**
     * 获取用户信息
     */
    public static SecurityUserDetails getUser() {
        return getUser(false);
    }

    /**
     * 获取用户信息
     *
     * @param reset 是否强制刷新缓存
     * @return 用户信息
     */
    public static SecurityUserDetails getUser(boolean reset) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        /* 这时候都变成了获取用户名了 因为jwt过滤器设置的*/
        Object username = authentication.getPrincipal();
        if (!reset) {
            SecurityUserDetails o = SpringContextUtils.getBean(RedisUtils.class).get(CacheCommonKeys.getSecurityUserKey(username.toString()));
            if (Objects.nonNull(o)) {
                return o;
            }
        }
        UserDetailsService bean1 = SpringContextUtils.getBean(UserDetailsService.class);
        if (username instanceof SecurityUserDetails) {
            username = ((SecurityUserDetails) username).getUsername();
        }
        try {
            return (SecurityUserDetails) bean1.loadUserByUsername(username.toString());
        } catch (UsernameNotFoundException e) {
            return null;
        }

    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return getUser().getId();
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return getUser().getDeptId();
    }
}
