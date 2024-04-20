package com.example.started.plan.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划
 *
 * @since 1.0.0
 */
@Data
@ApiModel(value = "计划")
public class PlanEventDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "日期")
    @JSONField(format = "yyyy-M-d")
    private Date dt;
    @ApiModelProperty(value = "文本")
    private String text;
    @ApiModelProperty(value = "颜色")
    private String color;
    @ApiModelProperty(value = "优先级")
    private Integer sort;

}
