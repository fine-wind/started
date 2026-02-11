package com.example.started.modules.posts;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.started.modules.auth.validate.dto.TokenUserId;

import java.util.List;

/**
 * 用户业务
 *
 * @since 1.0.0
 */
public interface PostsService extends IService<PostsEntity> {

    List<PostsFindVo> find(PostsFindBo body);

    PostsFindVo info(String id);

    List<PostsInfoCommentVo> infoComment(String id);

    void create(TokenUserId userId, PostsCreateBo body);
}
