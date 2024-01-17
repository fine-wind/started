package com.example.started.auth.modules.userConf.dto;

import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * 用户个性化配置
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "修改用户信息表单")
public class UserConfDto implements Serializable {
    @ApiModelProperty(value = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 配置项
     */
    private String userId;
    /**
     * 配置项
     */
    private String item;
    /**
     * 配置项内容
     */
    private String content;

    /**
     * 配置状态
     *
     * @see
     */
    private Integer status;
}
