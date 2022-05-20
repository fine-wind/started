package com.example.modules.job.dao;

import com.example.admin.job.entity.ScheduleJobLogEntity;
import com.example.common.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {

}
