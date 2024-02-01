package com.example.started.modules.job.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 定时任务
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleJobBo extends BaseBo {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * spring bean名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务状态  0：暂停  1：正常
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}
