package com.example.modules.sys.login.v0.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.cache.redis.RedisUtils;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.data.modules.log.service.SysLogLoginService;
import com.example.common.v0.data.modules.role.SysRoleUserService;
import com.example.common.v0.exception.ServerException;
import com.example.modules.log.enums.UserConfigBaseEnum;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.entity.SysUserEntity;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import com.example.modules.sys.userConf.entity.SysUserConfEntity;
import com.example.modules.sys.userConf.service.UserConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    private final SysLogLoginService logLoginService;
    private final RedisUtils redisUtils;

    private final SysRoleUserService sysRoleUserService;
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceV1 userServiceV1;

    @Autowired
    UserConfService userConfService;

    @Autowired
    public LoginServiceImpl(SysRoleUserService sysRoleUserService, SysLogLoginService logLoginService, RedisUtils redisUtils) {
        this.sysRoleUserService = sysRoleUserService;
        this.logLoginService = logLoginService;
        this.redisUtils = redisUtils;
    }

    /**
     * 登录或注册
     *
     * @param log 参数
     */
    @Override
    @Transactional
    public void join(LogInRegisterDTO log) {
        boolean b = userServiceV1.count(new LambdaQueryWrapper<>()) == 0;

        LambdaQueryWrapper<SysUserEntity> queryWrapper = userServiceV1.getQueryWrapper(new UserDto());
        queryWrapper.and(qw -> qw.eq(SysUserEntity::getUsername, log.getUsername()));

        Long count = userServiceV1.count(queryWrapper);
        if (count > 0) {
            throw new ServerException("用户已存在");
        }
        SysUserEntity user = new SysUserEntity();
        user.setUsername(log.getUsername());
        user.setPassword(passwordEncoder.encode(log.getPassword()));
        user.setCreator(String.valueOf(Constant.Status.UNKNOWN));
        userServiceV1.insert(user);
        if (b) {
            userConfService.insert(new SysUserConfEntity(user.getId(), UserConfigBaseEnum.SUPERMAN, Boolean.toString(true)));
        }
    }


    @Override
    public List<String> getUserIdListByDeptId(List<Long> deptIdList) {
//        queryWrapper.select(SysUserEntity::getId);
        UserDto params = new UserDto().setDeptIds(deptIdList);
        return userServiceV1.selectList(params).stream().map(UserDto::getId).collect(Collectors.toList());
    }

    @Override
    public Long getCountByDeptId(Long deptId) {
        UserDto user = new UserDto();
        user.setDeptId(deptId);
        return userServiceV1.count(userServiceV1.getQueryWrapper(user));
    }

}
