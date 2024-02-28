package com.example.started.api.modules.dict.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型
 */
@Data
@ApiModel(value = "字典类型")
public class SysDictTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "字典类型")
    @NotBlank(message = "{sysdict.type.require}", groups = DefaultGroup.class)
    private String dictType;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "{sysdict.name.require}", groups = DefaultGroup.class)
    private String dictName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updateDate;
}
