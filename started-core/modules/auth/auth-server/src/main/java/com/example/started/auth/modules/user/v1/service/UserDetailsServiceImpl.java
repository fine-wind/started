package com.example.started.auth.modules.user.v1.service;

import com.example.common.v0.constant.Constant;
import com.example.started.auth.client.conf.AuthClientUserDetailsServiceImpl;
import com.example.started.auth.modules.userConf.params.redis.SysParamsRedis;
import com.example.started.auth.modules.role.SysRoleUserService;
import com.example.started.auth.modules.user.v1.dto.UserDto;
import com.example.started.auth.modules.userConf.service.UserConfService;
import com.example.started.auth.client.role.SysRoleUserRedis;
import com.example.started.auth.client.user.SecurityUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Log4j2
@AllArgsConstructor
public class UserDetailsServiceImpl extends AuthClientUserDetailsServiceImpl implements UserDetailsPasswordService {

    private final UserServiceV1 userService;
    private final UserConfService masterUserService;
    private final SysRoleUserService roleUserService;
    private final SysRoleUserRedis roleUserRedis;
    private final SysParamsRedis paramsRedis;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userService.getByName(username);
        /* 如果是新的 就注册*/
        if (Objects.isNull(user)) {
            user = new UserDto();
            user.setUsername(username);
            throw new UsernameNotFoundException("用户名不存在异常");
        }

        SecurityUserDetails userDetails = new SecurityUserDetails();

        BeanUtils.copyProperties(user, userDetails);
        boolean master = masterUserService.isSuper(user.getId());
        /* 是否管理员*/
        userDetails.setSuperAdmin(master ? Constant.Status.SUCCESS : Constant.Status.FAIL);
        // 加载用户的所有权限到缓存内
        Set<String> roles = null;
        if (!master) {
            // 查询出角色
            List<Long> roleIdList = roleUserService.getRoleIdList(user.getId());
            // 根据角色查询出权限id
          //  List<Long> menuIdList = roleMenuService.getMenuIdLists(roleIdList);
            // 根据权限id查询出权限配置
          //  roles = menuService.getListByIds(menuIdList);
        } else {
         //   roles = menuService.getListByIds(null);
        }

        long tokenTime = paramsRedis.getTokenTime();

        // redisUtils.setCache(CacheCommonKeys.getSecurityUserKey(username), userDetails, tokenTime);
        roleUserRedis.setRoles(username, roles);
        return userDetails;
    }

    /**
     * todo 更改密码
     *
     * @param user        the user to modify the password for
     * @param newPassword the password to change to, encoded by the configured
     *                    {@code PasswordEncoder}
     * @return .
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // userService.updatePassword(user.getUsername(), newPassword);
        return user;
    }

}
