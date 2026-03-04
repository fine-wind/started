package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.exception.AppException;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

/**
 * service
 */
@Log4j2
@Service
@AllArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;
    private static Integer registerCount = 0;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true, timeout = 5, rollbackFor = Exception.class)
    public AuthUserEntity getByUserId(String userId) {
        return authUserRepository.findById(userId).get();
    }

    @Override
    public AuthUserEntity getByUsername(String username) {
        return authUserRepository.findByUsernameEquals(username);
    }

    @Override
    public List<AuthUserAllVo> all(String userId, AuthUserAllBo bo) {

        Specification<AuthUserEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

//
//            if (params.getEndDate() != null) {
//                predicates.add(cb.lessThanOrEqualTo(root.get("dt"), params.getEndDate()));
//            }

            // order by
            query.orderBy(cb.desc(root.get("dt")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<AuthUserEntity> authUserEntities = authUserRepository.findAll(spec);

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
            authUserRepository.save(entity);
        } catch (DuplicateKeyException e) {
            throw new AppException("请更改用户名");
        }

        log.info("用户注册成功: {}", username);
    }

    @Override
    public long count(String userId, AuthUserAllBo bo) {
return authUserRepository.count();
    }

}
