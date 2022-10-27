package com.example.common.v0.data.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 验证码
 *
 * @since 1.0.0 2020-07-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "验证码")
public class VerifyCodeBo extends BaseBo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private String mobile;

    @ApiModelProperty(value = "")
    private String loginName;

    @ApiModelProperty(value = "")
    private String code;

    @ApiModelProperty(value = "")
    private Integer state;


}
