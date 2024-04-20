package com.example.started.plan.bo;

import com.example.common.v1.base.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanEventBo extends BaseDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dt;
    private String text;
    private String color;
    private Integer sort;
}
