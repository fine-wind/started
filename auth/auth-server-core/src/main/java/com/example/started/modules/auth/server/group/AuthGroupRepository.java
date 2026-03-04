package com.example.started.modules.auth.server.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @since 1.0.0
 */
@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroupEntity, String> {

}
