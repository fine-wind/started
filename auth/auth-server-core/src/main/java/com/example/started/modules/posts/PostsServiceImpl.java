package com.example.started.modules.posts;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.started.common.v0.utils.ConvertUtils;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, PostsEntity> implements PostsService {


    @Override
    public List<PostsFindVo> find(PostsFindBo body) {
        LambdaQueryWrapper<PostsEntity> where = new LambdaQueryWrapper<>();
        where.last("limit 10");
        List<PostsEntity> postsEntities = baseMapper.selectList(where);
        return ConvertUtils.sourceToTarget(postsEntities, PostsFindVo.class);
    }

    @Override
    public PostsFindVo info(String id) {
        PostsEntity postsEntity = baseMapper.selectById(id);
        return ConvertUtils.sourceToTarget(postsEntity, PostsFindVo.class);
    }

    @Override
    public List<PostsInfoCommentVo> infoComment(String id) {
        LambdaQueryWrapper<PostsEntity> eq = new LambdaQueryWrapper<>();
        eq.eq(PostsEntity::getParentId, id);
        eq.last("limit 10");
        List<PostsEntity> postsEntities = baseMapper.selectList(eq);
        return ConvertUtils.sourceToTarget(postsEntities, PostsInfoCommentVo.class);
    }
}
