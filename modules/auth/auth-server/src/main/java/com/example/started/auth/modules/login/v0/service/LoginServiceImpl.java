package com.example.started.auth.modules.login.v0.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.enums.UserConfigBaseEnum;
import com.example.common.v0.exception.ServerException;
import com.example.started.auth.modules.role.SysRoleUserService;
import com.example.started.auth.modules.user.v1.dto.UserDto;
import com.example.started.auth.modules.user.v1.entity.SysUserEntity;
import com.example.started.auth.modules.user.v1.service.UserServiceV1;
import com.example.started.auth.modules.userConf.entity.SysUserConfEntity;
import com.example.started.auth.modules.userConf.service.UserConfService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceV1 userServiceV1;
    private final UserConfService userConfService;


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

    @Override
    public long aLogin(String username) {
        return 0;// todo redisUtils.incr(CacheCommonKeys.getUserLoginCs(username), 60);
    }

    @Override
    public long login(String username) {
        Number aLong = 0; // todo  redisUtils.get(CacheCommonKeys.getUserLoginCs(username));
        return Objects.isNull(aLong) ? 0 : aLong.longValue();
    }

}
