package com.example.started.modules.job.bo;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 定时任务日志
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleJobLogBo extends BaseBo implements Serializable {
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
