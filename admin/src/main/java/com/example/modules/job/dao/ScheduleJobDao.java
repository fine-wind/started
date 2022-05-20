package com.example.modules.job.dao;

import com.example.admin.job.entity.ScheduleJobEntity;
import com.example.common.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

}
