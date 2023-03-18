package com.example.common.v0.data.dto;

import com.example.common.v0.validator.group.AddGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 登录注册表单
 * <p>
 * email xingii@outlook.com
 * datetime 2020-8-19 10:34
 *
 * @author 行星
 */
@Data
@ApiModel(value = "修改用户信息表单")
public class LogInRegisterDTO {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class})
    private String username;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class})
    private String email;

    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    @Size(min = 8, message = "密码最短8位", groups = {AddGroup.class})
    private String password;

}
