package com.example.modules.job.service;

import com.example.admin.job.entity.ScheduleJobEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.modules.job.bo.ScheduleJobBo;
import com.example.modules.job.dto.ScheduleJobDTO;

/**
 * 定时任务
 */
public interface ScheduleJobService extends BaseService<ScheduleJobBo, ScheduleJobEntity> {

    PageData<ScheduleJobDTO> page(ScheduleJobBo params);

    ScheduleJobDTO get(Long id);

    /**
     * 保存定时任务
     */
    void save(ScheduleJobDTO dto);

    /**
     * 更新定时任务
     */
    void update(ScheduleJobDTO dto);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] ids);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] ids, int status);

    /**
     * 立即执行
     */
    void run(Long[] ids);

    /**
     * 暂停运行
     */
    void pause(Long[] ids);

    /**
     * 恢复运行
     */
    void resume(Long[] ids);
}
