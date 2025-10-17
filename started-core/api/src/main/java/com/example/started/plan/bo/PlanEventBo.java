package com.example.started.plan.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Data
public class PlanEventBo {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dt;
    private String text;
    private String color;
    private Integer sort;
}
