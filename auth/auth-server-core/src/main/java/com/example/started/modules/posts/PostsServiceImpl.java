package com.example.started.modules.posts;

import com.example.started.common.v0.utils.ConvertUtils;
import com.example.started.common.v0.utils.StringUtil;
import com.example.started.demo.cache.RedisUtils;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * service
 */
@Log4j2
@Service
@AllArgsConstructor
public class PostsServiceImpl implements PostsService {

    private PostsRepository baseMapper;


    private final RedisUtils redisUtils;

    @Override
    public List<PostsFindVo> find(PostsFindBo body) {

        if (Objects.isNull(body.getLastDt())) {
            body.setLastDt(new Date());
        }
        Specification<PostsEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // parentId is null
            predicates.add(cb.isNull(root.get("parentId")));

            // title like (如果title不为空)
            if (Objects.nonNull(body.getTitle())) {
                predicates.add(cb.like(root.get("content"), "%" + body.getTitle() + "%"));
            }

            // 根据 searchType 添加不同的条件
            String searchType = body.getSearchType();
            Path<Date> orderField;

            if ("createdAt".equals(searchType)) {
                predicates.add(cb.lessThan(root.get("createdAt"), body.getLastDt()));
                orderField = root.get("createdAt");
            } else if ("lastCommentAt".equals(searchType)) {
                predicates.add(cb.lessThan(root.get("lastCommentAt"), body.getLastDt()));
                orderField = root.get("lastCommentAt");
            } else {
                orderField = root.get("createdAt"); // 默认排序字段
            }

            // 应用排序
            query.orderBy(cb.desc(orderField));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 分页限制（取前10条）
        Pageable pageable = PageRequest.of(0, 10);

        Slice<PostsEntity> slice = baseMapper.findAll(spec, pageable);
        List<PostsEntity> postsEntities = slice.getContent();
        List<PostsFindVo> postsFindVos = ConvertUtils.sourceToTarget(postsEntities, PostsFindVo.class);
        postsFindVos.forEach(e -> {
            String content = StringUtil.defaultValue(e.getContent(), "");
            e.setContent(Objects.nonNull(content) && content.length() > 100 ? content.substring(0, 100) + "……………" : content);
        });
        return postsFindVos;
    }

    @Override
    public PostsFindVo info(TokenUserId userId, PostsInfoBo bo) {
        String id = bo.getId();
        PostsEntity postsEntity;
        if ("random".equals(id)) {
            postsEntity = this.random();
        } else {
            postsEntity = baseMapper.findById(id).get();
        }
        id = postsEntity.getId();
        boolean lockPv = redisUtils.lock("pv:" + id + ":" + bo.getIp(), 1, TimeUnit.HOURS);
        if (lockPv) {
            postsEntity.setPv(postsEntity.getPv() + 1);
        }
        boolean lockUv = redisUtils.lock("pv:" + id + ":" + bo.getIp() + ":" + userId.getUserId(), 1, TimeUnit.HOURS);
        if (lockUv) {
            postsEntity.setUv(postsEntity.getUv() + 1);
        }
        if (lockPv && lockUv) {
            baseMapper.save(postsEntity);
        }
        return ConvertUtils.sourceToTarget(postsEntity, PostsFindVo.class);
    }

    public PostsEntity random() {
        Specification<PostsEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isNull(root.get("parentId")));

            query.where(predicates.toArray(new Predicate[0]));
            // MySQL 使用 RAND() 函数
            query.orderBy(cb.asc(cb.function("RAND", Double.class)));
            return cb.conjunction();
        };

        Pageable pageable = PageRequest.of(0, 1);
        Page<PostsEntity> page = baseMapper.findAll(spec, pageable);
        return page.getContent().isEmpty() ? null : page.getContent().get(0);
    }

    @Override
    public List<PostsInfoCommentVo> infoComment(String id) {
        List<PostsEntity> postsEntities = baseMapper.findByParentId(id);
        return ConvertUtils.sourceToTarget(postsEntities, PostsInfoCommentVo.class);
    }

    @Override
    public void create(TokenUserId userId, PostsCreateBo body) {
        PostsEntity entity = new PostsEntity();
        BeanUtils.copyProperties(body, entity);
        entity.setFromId(userId.getUserId());
        entity.setState("1");
        entity.setAnon(0);
        entity.setDis(0);
        entity.setCircleId(0);
        entity.setUv(0);
        entity.setPv(0);
        entity.setIsHide(0);
        entity.setCreatedAt(new Date());
        entity.setDelFlag(0);
        baseMapper.save(entity);
    }
}
