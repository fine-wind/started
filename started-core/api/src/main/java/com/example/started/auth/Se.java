package com.example.started.auth;

import com.example.started.auth.role.user.SecurityUser;
import com.example.common.v3.cache.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 安全
 */
@Component(value = "se")
@AllArgsConstructor
public class Se {
    private final RedisUtils redisUtils;

    public boolean login() {
        return Objects.isNull(SecurityUser.getUserName());
    }

    /**
     * 校验是否具有某个角色
     * todo 自动创建资源
     *
     * @param roles 可访问的角色列表
     * @return 校验结果
     */
    public boolean hasRole(String... roles) {
        if (roles.length == 0) {
            return false;
        }
        String userId = SecurityUser.getUserId();
        for (String role : roles) {
            boolean b = redisUtils.hashHasKey(userId, role);
            if (b) {
                return true;
            }
        }
        return false;
    }
}
