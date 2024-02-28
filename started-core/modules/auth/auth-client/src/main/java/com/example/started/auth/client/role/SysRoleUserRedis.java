package com.example.started.auth.client.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
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
    private final HashMap<String, Object> map = new HashMap<>();

    /**
     * @param username 用户名
     * @param roles    角色集合
     */
    public void setRoles(String username, Set<String> roles) {
        map.put(username, roles);
    }

    public Set<Object> getRoles(String username) {
        return (Set<Object>) map.getOrDefault(username, new HashSet<>(0, 0.1f));
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
//        String username = SecurityUser.getUser().getUsername();
//        Map<Object, Boolean> member = redisUtils.opsForSet().isMember(CacheCommonKeys.getSecurityRoleKey(username), roles);
//        assert member != null;
//        for (Boolean v : member.values()) {
//            b = v;
//            if (b) {
//                break;
//            }
//        }
        return b;
    }

}
