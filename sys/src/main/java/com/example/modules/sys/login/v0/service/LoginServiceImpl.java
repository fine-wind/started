package com.example.modules.sys.login.v0.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.v0.data.modules.log.service.SysLogLoginService;
import com.example.common.v0.data.modules.role.SysRoleUserService;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.HttpContextUtils;
import com.example.common.v0.utils.IpUtils;
import com.example.common.v0.utils.StringUtil;
import com.example.common.v1.base.service.impl.BaseServiceImpl;
import com.example.modules.security.PasswordConfig;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.user.v1.dao.UserDao;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.entity.SysUserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl extends BaseServiceImpl<UserDto, UserDao, SysUserEntity> implements LoginService {
    private final SysLogLoginService logLoginService;
    private final PasswordConfig passwordConfig;
    private final RedisUtils redisUtils;

    private final SysRoleUserService sysRoleUserService;

    @Autowired
    public LoginServiceImpl(SysRoleUserService sysRoleUserService, SysLogLoginService logLoginService, PasswordConfig passwordConfig, RedisUtils redisUtils) {
        this.sysRoleUserService = sysRoleUserService;
        this.logLoginService = logLoginService;
        this.passwordConfig = passwordConfig;
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
        LambdaQueryWrapper<SysUserEntity> queryWrapper = this.getQueryWrapper(new UserDto());
        queryWrapper.and(qw -> qw.eq(SysUserEntity::getUsername, log.getUsername()));

        Long count = baseDao.selectCount(queryWrapper);
        if (count > 0) {
            throw new ServerException("用户已存在");
        }
        SysUserEntity user = new SysUserEntity();
        user.setUsername(log.getUsername());
        user.setRealName(log.getEmail());
        user.setPassword(passwordConfig.encode(log.getPassword()));
        user.setEmail(log.getEmail());
        user.setCreator(String.valueOf(Constant.Status.UNKNOWN));
        this.insert(user);
        String userId = user.getId();

        SysLogLoginEntity loginLog = new SysLogLoginEntity();
        loginLog.setOperation(0);
        loginLog.setStatus(1);
        loginLog.setIp(IpUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        loginLog.setCreator(userId);
        logLoginService.insert(loginLog);
    }

    @Override
    public UserDto getByname(String username) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = this.getQueryWrapper(new UserDto());
        queryWrapper.and(qw -> qw.eq(SysUserEntity::getUsername, username));

        SysUserEntity userEntities = baseDao.selectOne(queryWrapper);
        if (Objects.isNull(userEntities)) {
            return null;
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(userEntities, dto);
        return dto;
    }

    @Override
    public UserDto getUserByUserId(Long userId) {
        SysUserEntity SysUserEntity = baseDao.selectById(userId);
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(SysUserEntity, dto);
        return dto;
    }


    @Override
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        SysUserEntity user = baseDao.selectById(userId);
        boolean matches = passwordConfig.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new ServerException("旧密码不正确");
        }
        newPassword = passwordConfig.encode(newPassword);

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(newPassword);
        sysUserEntity.setId(userId);

        baseDao.updateById(sysUserEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserDto userDTO) {
        SysUserEntity SysUserEntity = ConvertUtils.sourceToTarget(userDTO, SysUserEntity.class);
        String password = userDTO.getPassword();
        userDTO.setPassword(null);
        if (StringUtil.isNotEmpty(password)) {
            SysUserEntity.setPassword(passwordConfig.encode(password));
        }
        sysRoleUserService.saveOrUpdate(SysUserEntity.getId(), userDTO.getRoleIdList());
        baseDao.updateById(SysUserEntity);
    }


    @Override
    // @Cache(key = "sys:user:token")
    public SecurityUserDetails getUserInfoVo(String username) {
        String securityUserKey = CacheCommonKeys.getSecurityUserKey(username);
        SecurityUserDetails cache = redisUtils.getCache(securityUserKey);
        if (cache == null || cache.getUsername() == null) {
            UserDto byname = this.getByname(username);
            if (Objects.nonNull(byname)) {
                cache = new SecurityUserDetails();
                BeanUtils.copyProperties(byname, cache);
            }
            redisUtils.setCache(securityUserKey, cache);
        }
        return cache;
    }

    @Override
    public List<String> getUserIdListByDeptId(List<Long> deptIdList) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = getQueryWrapper(new UserDto().setDeptIds(deptIdList));
        queryWrapper.select(SysUserEntity::getId);
        return baseDao.selectList(queryWrapper).stream().map(SysUserEntity::getId).collect(Collectors.toList());
    }

    @Override
    public Long getCountByDeptId(Long deptId) {
        UserDto user = new UserDto();
        user.setDeptId(deptId);
        return baseDao.selectCount(this.getQueryWrapper(user));
    }

    @Override
    public LambdaQueryWrapper<SysUserEntity> getQueryWrapper(UserDto params) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.like(StringUtil.isNotEmpty(params.getUsername()), SysUserEntity::getUsername, params.getUsername());
        queryWrapper.like(StringUtil.isNotEmpty(params.getEmail()), SysUserEntity::getEmail, params.getEmail());
        queryWrapper.like(StringUtil.isNotEmpty(params.getMobile()), SysUserEntity::getMobile, params.getMobile());
        queryWrapper.eq(Objects.nonNull(params.getGender()), SysUserEntity::getGender, params.getGender());
        queryWrapper.eq(Objects.nonNull(params.getDeptId()), SysUserEntity::getDeptId, params.getDeptId());

        queryWrapper.in(Objects.nonNull(params.getDeptIds()), SysUserEntity::getDeptId, params.getDeptIds());

        return queryWrapper;
    }
}
