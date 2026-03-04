package com.example.started.modules.posts;

import com.example.started.modules.auth.validate.dto.TokenUserId;

import java.util.List;

/**
 * 用户业务
 *
 * @since 1.0.0
 */
public interface PostsService {

    List<PostsFindVo> find(PostsFindBo body);

    PostsFindVo info(TokenUserId userId, PostsInfoBo bo);

    List<PostsInfoCommentVo> infoComment(String id);

    void create(TokenUserId userId, PostsCreateBo body);
}
