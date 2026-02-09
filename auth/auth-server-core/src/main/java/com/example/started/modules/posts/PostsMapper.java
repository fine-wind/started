package com.example.started.modules.posts;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.started.modules.auth.server.sys.user.AuthUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @since 1.0.0
 */
@Mapper
public interface PostsMapper extends BaseMapper<PostsEntity> {

}
