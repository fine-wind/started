package com.example.started.auth;

import com.example.common.v0.utils.SpringContextUtils;
import com.example.common.v3.cache.RedisUtils;
import com.example.started.auth.role.user.SecurityUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 安全
 */
@Component(value = "se")
@AllArgsConstructor
public class Se {
    private final RedisUtils redisUtils;

    private static String getCacheUserInfoKey(Long userId) {
        return "user:info:" + userId;
    }

    /**
     * @return 获取用户登录名
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = null;
        if (Objects.nonNull(authentication)) {
            username = authentication.getPrincipal();
        }
        return String.valueOf(username);
    }

    /**
     * 缓存数据
     *
     * @param seUser 登录用户
     */
    public static void put(SecurityUserDetails seUser) {
        Se se = SpringContextUtils.getBean(Se.class);
        String key = getCacheUserInfoKey(seUser.getId());
        se.redisUtils.hashSet(key, "id", seUser.getId());
        se.redisUtils.hashSet(key, "username", seUser.getUsername());
        se.redisUtils.hashSet(key, "superAdmin", seUser.getSuperAdmin());
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        Se se = SpringContextUtils.getBean(Se.class);
        return se.redisUtils.hashGet("user:id:" + getUserName(), "id");
    }

    /**
     * todo 判断用户是不是系统管理员
     */
    public static Boolean SUPER_USER() {
        Se se = SpringContextUtils.getBean(Se.class);
        Boolean superAdmin = se.redisUtils.hashGet(getCacheUserInfoKey(getUserId()), "superAdmin");
        return Objects.nonNull(superAdmin) && superAdmin;
    }

    /**
     * 校验是否具有某个角色
     * todo 自动创建资源？
     *
     * @param roles 可访问的角色列表
     * @return 校验结果
     */
    public boolean hasRole(String... roles) {
        if (roles.length == 0) {
            return true;
        }
        if (SUPER_USER()) {
            return true;
        }
        Long userId = getUserId();
        for (String role : roles) {
            boolean b = redisUtils.hasHashKey(String.valueOf(userId), role);
            if (b) {
                return true;
            }
        }
        return false;
    }
}
