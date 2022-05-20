package com.example.modules.job.service;

import com.example.admin.job.bo.ScheduleJobLogBo;
import com.example.admin.job.entity.ScheduleJobLogEntity;
import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.modules.job.dto.ScheduleJobLogDTO;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogBo, ScheduleJobLogEntity> {

    PageData<ScheduleJobLogDTO> page(ScheduleJobLogBo params);

    ScheduleJobLogDTO get(Long id);
}
