package com.example.started.modules.auth.server.sys.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @since 1.0.0
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserEntity, String>, JpaSpecificationExecutor<AuthUserEntity> {


    AuthUserEntity findByUsernameEquals(String username);

}
