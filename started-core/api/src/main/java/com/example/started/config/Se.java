package com.example.started.config;

import com.example.started.auth.role.user.SecurityUser;
import com.example.started.redis.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Filter配置
 */
@Component("sf")
@AllArgsConstructor
public class Se {
    private final RedisUtils redisUtils;

    public boolean login() {
        return Objects.isNull(SecurityUser.getUserName());
    }

    /**
     * 校验是否具有某个角色
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
