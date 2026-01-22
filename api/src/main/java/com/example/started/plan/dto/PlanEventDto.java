package com.example.started.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划
 *
 * @since 1.0.0
 */
@Data
public class PlanEventDto implements Serializable {

    private Long id;
    /**
     * 日期
     */
    // @JSONField(format = "yyyy-MM-dd")
    private Date dt;
    /**
     * 文本
     */
    private String text;
    /**
     * 颜色
     */
    private String color;
    /**
     * 优先级
     */
    private Integer sort;

}
