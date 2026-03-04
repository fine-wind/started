package com.example.started.demo.modules.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @since 1.0.0
 */
@Repository
public interface DemoTableRepository extends JpaRepository<DemoTableEntity, String> {

}
