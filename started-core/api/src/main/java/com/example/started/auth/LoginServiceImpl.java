package com.example.started.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.exception.ServerException;
import com.example.started.modules.sys.dao.SysUserDao;
import com.example.started.modules.sys.dao.SysUserSuperDao;
import com.example.started.modules.sys.entity.SysUserEntity;
import com.example.started.modules.sys.entity.SysUserSuperEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserDao sysUserDao;
    private final SysUserSuperDao sysUserSuperDao;

    @Override
    @Transactional
    public void join(LogInRegisterDTO mobile) {

        Long l = sysUserDao.selectCount(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, mobile.getUsername()));
        if (l > 0) {
            throw new ServerException("此用户名已存在，请从新输入或者找回密码");
        }

        SysUserEntity entity = new SysUserEntity();
        entity.setUsername(mobile.getUsername());
        entity.setPassword(passwordEncoder.encode(mobile.getPassword()));
        sysUserDao.insert(entity);
        log.debug(entity.getId());
        sysUserSuperDao.insert(new SysUserSuperEntity());
    }

    @Override
    public long login(String username) {
        return 0;
    }
}
