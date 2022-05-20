package com.example.modules.sys.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.cache.redis.RedisUtils;
import com.example.cache.constant.CacheCommonKeys;
import com.example.common.constant.Constant;
import com.example.common.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.data.modules.log.service.SysLogLoginService;
import com.example.common.data.modules.role.SysRoleUserService;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.common.utils.*;
import com.example.modules.security.PasswordConfig;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.user.bo.UserBo;
import com.example.modules.sys.user.dao.UserDao;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.entity.SysUserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.common.exception.ServerException;
import com.example.common.data.dto.LogInRegisterDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends CrudServiceImpl<UserBo, UserDao, SysUserEntity, UserDto> implements UserService {
    private final SysLogLoginService logLoginService;
    private final PasswordConfig passwordConfig;
    private final RedisUtils redisUtils;

    private final SysRoleUserService sysRoleUserService;

    @Autowired
    public UserServiceImpl(SysRoleUserService sysRoleUserService, SysLogLoginService logLoginService, PasswordConfig passwordConfig, RedisUtils redisUtils) {
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
        LambdaQueryWrapper<SysUserEntity> queryWrapper = this.getQueryWrapper(new UserBo());
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
        user.setCreator(Constant.Status.UNKNOWN);
        this.insert(user);
        Long userId = user.getId();

        SysLogLoginEntity loginLog = new SysLogLoginEntity();
        loginLog.setOperation(0);
        loginLog.setStatus(1);
        loginLog.setIp(IpUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        loginLog.setCreator(userId);
        logLoginService.insert(loginLog);
    }

    @Override
    public UserDto getByname(String username) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = this.getQueryWrapper(new UserBo());
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
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
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
    public List<Long> getUserIdListByDeptId(List<Long> deptIdList) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = getQueryWrapper(new UserBo().setDeptIds(deptIdList));
        queryWrapper.select(SysUserEntity::getId);
        return baseDao.selectList(queryWrapper).stream().map(SysUserEntity::getId).collect(Collectors.toList());
    }

    @Override
    public Long getCountByDeptId(Long deptId) {
        UserBo user = new UserBo();
        user.setDeptId(deptId);
        return baseDao.selectCount(this.getQueryWrapper(user));
    }

    @Override
    public LambdaQueryWrapper<SysUserEntity> getQueryWrapper(UserBo params) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = super.getQueryWrapper(params);

//        queryWrapper.and(qw ->qw.)
        queryWrapper.like(StringUtil.isNotEmpty(params.getUsername()), SysUserEntity::getUsername, params.getUsername());
        queryWrapper.like(StringUtil.isNotEmpty(params.getEmail()), SysUserEntity::getEmail, params.getEmail());
        queryWrapper.like(StringUtil.isNotEmpty(params.getMobile()), SysUserEntity::getMobile, params.getMobile());
        queryWrapper.eq(Objects.nonNull(params.getGender()), SysUserEntity::getGender, params.getGender());
        queryWrapper.eq(Objects.nonNull(params.getDeptId()), SysUserEntity::getDeptId, params.getDeptId());

        queryWrapper.in(Objects.nonNull(params.getDeptIds()), SysUserEntity::getDeptId, params.getDeptIds());

        return queryWrapper;
    }
}
