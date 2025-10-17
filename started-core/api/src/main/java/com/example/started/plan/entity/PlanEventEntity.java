package com.example.started.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.dynamic.datasource.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 计划任务
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plan_event")
public class PlanEventEntity extends BaseEntity {

    private Date dt;
    private String text;
    private String color;
    private Integer sort;
}
