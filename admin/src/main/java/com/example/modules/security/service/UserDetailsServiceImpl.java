package com.example.modules.security.service;

import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.modules.role.SysRoleUserService;
import com.example.common.v0.modules.params.redis.SysParamsRedis;
import com.example.modules.master.service.SuperUserService;
import com.example.modules.security.role.SysRoleUserRedis;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.service.SysMenuService;
import com.example.modules.sys.service.SysRoleMenuService;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    private UserServiceV1 userService;
    @Autowired
    private SuperUserService masterUserService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysRoleUserService roleUserService;
    @Autowired
    private SysRoleUserRedis roleUserRedis;

    @Autowired
    private SysRoleMenuService roleMenuService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysParamsRedis paramsRedis;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // todo 登录到达一定次数时，需要验证码
        SecurityUserDetails userDetails = redisUtils.getCache(CacheCommonKeys.getSecurityUserKey(username));
//        if (Objects.nonNull(userDetails)) {
//            return userDetails;
//        }
        UserDto user = userService.getByName(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在异常");
        }

        userDetails = new SecurityUserDetails();

        BeanUtils.copyProperties(user, userDetails);
        boolean master = masterUserService.isSuper(user.getId());
        /* 是否管理员*/
        userDetails.setSuperAdmin(master ? Constant.Status.SUCCESS : Constant.Status.FAIL);
        // 加载用户的所有权限到缓存内
        Set<String> roles;
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
        roleUserRedis.setRoles(username, roles);
        return userDetails;
    }

    /**
     * todo 更改密码
     *
     * @param user        the user to modify the password for
     * @param newPassword the password to change to, encoded by the configured
     *                    {@code PasswordEncoder}
     * @return
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // userService.updatePassword(user.getUsername(), newPassword);
        return user;
    }

}
