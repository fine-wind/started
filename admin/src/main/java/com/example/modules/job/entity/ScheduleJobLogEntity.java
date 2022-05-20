package com.example.admin.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.common.data.entity.BaseEntity;

import java.io.Serializable;

/**
 * 定时任务日志
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("schedule_job_log")
public class ScheduleJobLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务id
     */
    private Long jobId;
    /**
     * spring bean名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * 任务状态    0：失败    1：成功
     */
    private Integer status;
    /**
     * 失败信息
     */
    private String error;
    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;

}
