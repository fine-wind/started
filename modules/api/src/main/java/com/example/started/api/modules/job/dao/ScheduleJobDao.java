package com.example.started.api.modules.job.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.api.modules.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

}
