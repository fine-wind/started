package com.example.modules.security.service;

import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.constant.Constant;
import com.example.common.data.modules.role.SysRoleUserService;
import com.example.common.modules.params.redis.SysParamsRedis;
import com.example.modules.master.service.MasterUserService;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.service.SysMenuService;
import com.example.modules.sys.service.SysRoleMenuService;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private MasterUserService masterUserService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysRoleUserService roleUserService;

    @Autowired
    private SysRoleMenuService roleMenuService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysParamsRedis paramsRedis;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getByname(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在异常");
        }

        SecurityUserDetails userDetails = new SecurityUserDetails();

        BeanUtils.copyProperties(user, userDetails);
        boolean master = masterUserService.isMaster(user.getId());
        /* 是否管理员*/
        userDetails.setSuperAdmin(master ? Constant.Status.SUCCESS : Constant.Status.FAIL);
        // 加载用户的所有权限到缓存内
        List<String> roles;
        if (!master) {
            // 查询出角色
            List<Long> roleIdList = roleUserService.getRoleIdList(user.getId());
            // 根据角色查询出权限id
            List<Long> menuIdList = roleMenuService.getMenuIdLists(roleIdList);
            // 根据权限id查询出权限配置
            roles = menuService.getListByIds(menuIdList);
        } else {
            roles = menuService.getListByIds(null);
        }

        long tokenTime = paramsRedis.getTokenTime();

        redisUtils.setCache(CacheCommonKeys.getSecurityUserKey(username), userDetails, tokenTime);
        redisUtils.setCache(CacheCommonKeys.getSecurityRoleKey(username), roles, tokenTime);
        return userDetails;
    }
}