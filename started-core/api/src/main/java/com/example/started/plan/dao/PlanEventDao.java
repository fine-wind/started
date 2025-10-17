package com.example.started.plan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.started.plan.entity.PlanEventEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Mapper
public interface PlanEventDao extends BaseMapper<PlanEventEntity> {

}
