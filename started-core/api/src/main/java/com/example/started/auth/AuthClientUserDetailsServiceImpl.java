package com.example.started.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.exception.ServerException;
import com.example.started.modules.sys.dao.SysUserDao;
import com.example.started.modules.sys.entity.SysUserEntity;
import com.example.started.modules.table.service.TableDbService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (Objects.isNull(username) || username.length() < 6) {
            throw new UsernameNotFoundException("没有此用户.");
        }
        log.debug("login user is {}", username);
        SysUserEntity sysUserEntity = sysUserDao.selectOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, username));
        if (Objects.isNull(sysUserEntity)) {
            throw new UsernameNotFoundException("没有此用户。");
        }
        return new User(username, sysUserEntity.getPassword(), new ArrayList<>());
    }
}
