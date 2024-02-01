package com.example.started.modules.job.service;

import com.example.started.modules.job.bo.ScheduleJobLogBo;
import com.example.started.modules.job.dto.ScheduleJobLogDTO;
import com.example.started.modules.job.entity.ScheduleJobLogEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogBo, ScheduleJobLogEntity> {

    PageData<ScheduleJobLogDTO> page(ScheduleJobLogBo params);

    ScheduleJobLogDTO get(Long id);
}
