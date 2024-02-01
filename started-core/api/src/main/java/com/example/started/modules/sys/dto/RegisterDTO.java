package com.example.started.modules.sys.dto;

import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 注册表单
 */
@Data
@ApiModel(value = "注册表单")
public class RegisterDTO {

//    @ApiModelProperty(value = "用户名", required = true)
//    @NotBlank(message="{sysuser.username.require}", groups = DefaultGroup.class)
//    private String username;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "{sysuser.realname.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "(^1[3|4|5|6|7|8|9]\\d{9}$)|(^09\\d{8}$)", message = "手机号格式不正确")
    private String mobile;

    @ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
    @Max(value = 2, message = "{sysuser.gender.range}", groups = AddGroup.class)
    @Min(value = 0, message = "{sysuser.gender.range}", groups = AddGroup.class)
    private Integer gender;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "身份证号", required = true)
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)", message = "身份证格式不正确")
    private String idCard;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class})
    private String email;

}
