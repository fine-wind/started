package com.example.started.modules.job.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {

}
