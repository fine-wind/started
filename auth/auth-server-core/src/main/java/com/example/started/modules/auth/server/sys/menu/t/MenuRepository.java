package com.example.started.modules.auth.server.sys.menu.t;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @since 1.0.0
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {

}
