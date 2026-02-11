package com.example.started.modules.auth.server.sys.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.started.common.exception.AppException;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * service
 */
@Log4j2
@Service
@AllArgsConstructor
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUserEntity> implements AuthUserService {

    private static Integer registerCount = 0;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true, timeout = 5, rollbackFor = Exception.class)
    public AuthUserEntity getByUserId(String userId) {
        LambdaQueryWrapper<AuthUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUserEntity::getId, userId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public AuthUserEntity getByUsername(String username) {
        LambdaQueryWrapper<AuthUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUserEntity::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Long count(String userId, AuthUserAllBo bo) {
        LambdaQueryWrapper<AuthUserEntity> queryWrapper = this.where(userId, bo);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<AuthUserAllVo> all(String userId, AuthUserAllBo bo) {
        LambdaQueryWrapper<AuthUserEntity> queryWrapper = this.where(userId, bo);
        queryWrapper.last(bo.getPage().toLimit());
        List<AuthUserEntity> authUserEntities = baseMapper.selectList(queryWrapper);

        LinkedList<AuthUserAllVo> authUserAllVos = new LinkedList<>();
        authUserEntities.forEach(e -> authUserAllVos.add(new AuthUserAllVo(e)));
        return authUserAllVos;
    }

    @Override
    @Transactional(timeout = 5, rollbackFor = Exception.class)
    public void register(TokenUserId tokenUserId, AuthUserAddBo bo) {
        String username = bo.getUsername();

        if (registerCount++ > 10000) {
            throw new AppException("暂停注册");
        }
        if (this.count() > 10000) {
            throw new AppException("暂停注册");
        }
        // 检查用户名是否已存在
        if (this.getByUserId(username) != null) {
            throw new AppException("用户名已存在");
        }

        String encode = passwordEncoder.encode(bo.getPassword());
        AuthUserEntity entity = new AuthUserEntity();
        entity.setUsername(username);
        entity.setPassword(encode);
        entity.setStatus(100); // 注册后设为激活状态
        try {
            baseMapper.insert(entity);
        } catch (DuplicateKeyException e) {
            throw new AppException("请更改用户名");
        }

        log.info("用户注册成功: {}", username);
    }

    private LambdaQueryWrapper<AuthUserEntity> where(String userId, AuthUserAllBo bo) {
        LambdaQueryWrapper<AuthUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        return queryWrapper;
    }

}
