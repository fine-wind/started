package com.example.started.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.constant.Constant;
import com.example.started.auth.role.user.SecurityUserDetails;
import com.example.started.modules.sys.dao.*;
import com.example.started.modules.sys.entity.SysUserConfigEntity;
import com.example.started.modules.sys.entity.SysUserEntity;
import com.example.started.modules.table.service.TableDbService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Log4j2
@Component
@AllArgsConstructor
public class AuthClientUserDetailsServiceImpl implements UserDetailsService {

    final TableDbService tableDbService;

    final SysUserDao sysUserDao;
    final SysUserConfigDao sysUserConfigDao;

    @Override
    public SecurityUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (Objects.isNull(username) || username.length() < 6) {
            throw new UsernameNotFoundException("没有此用户.");
        }
        log.debug("login user is {}", username);
        SysUserEntity sysUserEntity = sysUserDao.selectOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, username));
        if (Objects.isNull(sysUserEntity)) {
            throw new UsernameNotFoundException("没有此用户。");
        }
        SecurityUserDetails seUser = new SecurityUserDetails();
        BeanUtils.copyProperties(sysUserEntity, seUser);
        seUser.setAuthorities(new ArrayList<>());

        seUser.setSuperAdmin(this.isSuper(seUser));

        Se.put(seUser);
        return seUser;
    }

    private Integer isSuper(SecurityUserDetails seUser) {
        LambdaQueryWrapper<SysUserConfigEntity> eq = new LambdaQueryWrapper<SysUserConfigEntity>()
                .eq(SysUserConfigEntity::getItem, Constant.UserConfigItemEnum.SUPER_USER.getValue())
                .eq(SysUserConfigEntity::getUserId, String.valueOf(seUser.getId()));
        return sysUserConfigDao.selectCount(eq) > 0 ? Constant.Status.SUCCESS : Constant.Status.FAIL;
    }
}
