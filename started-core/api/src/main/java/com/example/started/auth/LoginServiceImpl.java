package com.example.started.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.exception.ServerException;
import com.example.started.modules.sys.dao.SysRoleDto;
import com.example.started.modules.sys.dao.SysRoleUserDao;
import com.example.started.modules.sys.dao.SysUserConfigDao;
import com.example.started.modules.sys.dao.SysUserDao;
import com.example.started.modules.sys.entity.SysRoleEntity;
import com.example.started.modules.sys.entity.SysRoleUserEntity;
import com.example.started.modules.sys.entity.SysUserConfigEntity;
import com.example.started.modules.sys.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Log4j2
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserDao sysUserDao;
    private final SysRoleDto sysRoleDto;
    private final SysRoleUserDao sysRoleUserDao;
    private final SysUserConfigDao sysUserConfigDao;

    @Override
    public boolean init() {
        return sysUserDao.selectCount(null) == 0;
    }

    @Override
    @Transactional
    public void join(LogInRegisterDTO mobile) {

        String username = mobile.getUsername();
        if (Objects.isNull(username) || username.length() < 6) {
            throw new ServerException("用户名长度应大于5");
        }
        String password = mobile.getPassword();
        if (Objects.isNull(password)) {
            throw new ServerException("请输入密码");
        }
        Long l = sysUserDao.selectCount(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, username));
        if (l > 0) {
            throw new ServerException("此用户名已存在，请从新输入或者找回密码");
        }
        boolean init = init();
        SysUserEntity user = new SysUserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        sysUserDao.insert(user);
        if (init) {
            SysRoleEntity role = new SysRoleEntity();
            role.setName("系统管理员");
            role.setRemark("系统管理员");
            sysRoleDto.insert(role);

            SysRoleUserEntity roleUser = new SysRoleUserEntity();
            roleUser.setUserId(user.getId());
            roleUser.setRoleId(role.getId());
            sysRoleUserDao.insert(roleUser);

            SysUserConfigEntity entity = new SysUserConfigEntity();
            entity.setUserId(user.getId());
            entity.setItem(Constant.UserConfigItemEnum.SUPER_USER.getValue());
            sysUserConfigDao.insert(entity);
        }
    }

    @Override
    public long login(String username) {
        return 0;
    }

}
