package com.example.modules.sys.userConf.dto;

import com.example.common.annotation.DictTranslationClass;
import com.example.common.annotation.DictTranslationField;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.DefaultGroup;
import com.example.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户个性化配置
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "修改用户信息表单")
@DictTranslationClass
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
