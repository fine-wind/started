package com.example.started.plan.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.plan.entity.PlanEventEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Mapper
public interface PlanEventDao extends BaseDao<PlanEventEntity> {

}
