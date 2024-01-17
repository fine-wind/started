package com.example.started.api.modules.job.service;

import com.example.started.api.modules.job.bo.ScheduleJobLogBo;
import com.example.started.api.modules.job.dto.ScheduleJobLogDTO;
import com.example.started.api.modules.job.entity.ScheduleJobLogEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogBo, ScheduleJobLogEntity> {

    PageData<ScheduleJobLogDTO> page(ScheduleJobLogBo params);

    ScheduleJobLogDTO get(Long id);
}
