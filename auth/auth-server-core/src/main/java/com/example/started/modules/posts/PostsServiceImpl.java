package com.example.started.modules.posts;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.started.common.v0.utils.ConvertUtils;
import com.example.started.common.v0.utils.StringUtil;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

        if (Objects.isNull(body.getLastDt())) {
            body.setLastDt(new Date());
        }
        where.isNull(PostsEntity::getParentId);
        where.like(Objects.nonNull(body.getTitle()), PostsEntity::getContent, body.getTitle());
        switch (body.getSearchType()) {
            case "createdAt" ->
                    where.lt(PostsEntity::getCreatedAt, body.getLastDt()).orderByDesc(PostsEntity::getCreatedAt);
            case "lastCommentAt" ->
                    where.lt(PostsEntity::getLastCommentAt, body.getLastDt()).orderByDesc(PostsEntity::getLastCommentAt);
        }
        where.last("limit 10");
        List<PostsEntity> postsEntities = baseMapper.selectList(where);
        List<PostsFindVo> postsFindVos = ConvertUtils.sourceToTarget(postsEntities, PostsFindVo.class);
        postsFindVos.forEach(e -> {
            String content = StringUtil.defaultValue(e.getContent(), "");
            e.setContent(content.length() > 100 ? content.substring(0, 100) : content);
        });
        return postsFindVos;
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

    @Override
    public void create(TokenUserId userId, PostsCreateBo body) {
        PostsEntity entity = new PostsEntity();
        BeanUtils.copyProperties(body, entity);
        entity.setFromId(userId.getUserId());
        entity.setCircleId(0);
        entity.setUv(0);
        entity.setPv(0);
        entity.setCreatedAt(new Date());
        baseMapper.insert(entity);
    }
}
