package com.example.modules.security.role;

import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.modules.security.user.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 角色用户关系
 * <p>
 * 通过数据来确定权限 而不是编码处理
 *
 * @since 1.0.0
 */
@Service("SRUR")
public class SysRoleUserRedis {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param username 用户名
     * @param roles    角色集合
     */
    public void setRoles(String username, Set<String> roles) {
        redisUtils.opsSetAdd(CacheCommonKeys.getSecurityRoleKey(username), roles.toArray());
    }

    public Set<Object> getRoles(String username) {
        return redisUtils.members(CacheCommonKeys.getSecurityRoleKey(username));
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
        String username = SecurityUser.getUser().getUsername();
        Map<Object, Boolean> member = redisUtils.opsForSet().isMember(CacheCommonKeys.getSecurityRoleKey(username), roles);
        AtomicBoolean b = new AtomicBoolean(false);
        assert member != null;
        member.forEach((k, v) -> b.set(b.get() || v));
        return b.get();
    }


}
