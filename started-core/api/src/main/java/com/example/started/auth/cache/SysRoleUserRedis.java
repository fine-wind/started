package com.example.started.auth.cache;

import com.example.started.auth.Se;
import com.example.started.redis.CacheCommonKeys;
import com.example.common.v3.cache.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * 角色用户关系
 * <p>
 * 通过数据来确定权限 而不是编码处理
 *
 * @since 1.0.0
 */
@Service()
@AllArgsConstructor
public class SysRoleUserRedis {
    private final RedisUtils redisUtils;
    final String cacheKey = "sys:role:user:";

    /**
     * @param username 用户名
     * @param roles    角色集合
     */
    public void setRoles(String username, Set<String> roles) {
        redisUtils.setCache(cacheKey + username, roles);
    }

    /**
     * 是否包含某些角色
     *
     * @param roles 角色集合
     */
    public boolean hasRoles(String... roles) {
        if (roles == null || roles.length == 0) {
            return true;
        }
        boolean b = false;
        String username = Se.getUserName();
        Map<Object, Boolean> member = redisUtils.opsForSet().isMember(CacheCommonKeys.getSecurityRoleKey(username), roles);
        assert member != null;
        for (Boolean v : member.values()) {
            b = v;
            if (b) {
                break;
            }
        }
        return b;
    }

}
