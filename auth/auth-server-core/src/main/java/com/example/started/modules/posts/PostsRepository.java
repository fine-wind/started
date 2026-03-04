package com.example.started.modules.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @since 1.0.0
 */
@Repository
public interface PostsRepository extends JpaRepository<PostsEntity, String>, JpaSpecificationExecutor<PostsEntity> {

    List<PostsEntity> findByParentId(String id);
}
