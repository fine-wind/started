package com.example.started.auth.modules.user.v1.dto;

import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.example.common.v1.annotation.TiField;
import com.example.common.v1.base.BaseDto;
import com.example.started.auth.modules.sys.group.SysGroupEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 注册表单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "修改用户信息表单")
public class UserDto extends BaseDto implements Serializable {
    @ApiModelProperty(value = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private String id;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "{sysuser.username.require}", groups = DefaultGroup.class)
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{sysuser.password.require}", groups = AddGroup.class)
    private String password;

    @ApiModelProperty(value = "姓名or昵称", required = true)
    @NotBlank(message = "{sysuser.realname.require}", groups = UpdateGroup.class)
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空", groups = UpdateGroup.class)
    @Pattern(regexp = "(^1[3|4|5|6|7|8|9]\\d{9}$)|(^09\\d{8}$)", message = "手机号格式不正确")
    private String mobile;


    //    @ApiModelProperty(value = "身份证号", required = true)
//    @NotBlank(message="身份证号不能为空")
//    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)", message = "身份证格式不正确")
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空", groups = UpdateGroup.class)
    @Email(message = "{sysuser.email.error}", groups = UpdateGroup.class)
    private String email;

    @ApiModelProperty(value = "头像（fileid）", required = false)
    private String headUrl;


    @ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
    // todo  @Range(min = 0, max = 2, message = "{sysuser.gender.range}", groups = DefaultGroup.class)
    private Integer gender;

    @ApiModelProperty(value = "部门ID", required = true)
    @TiField(table = SysGroupEntity.class)
    private Long deptId;

    @ApiModelProperty(value = "状态  0：停用    1：正常", required = true)
    // todo @Range(min = 0, max = 1, message = "{sysuser.status.range}", groups = DefaultGroup.class)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    /**
     * 是否为超超级管理员
     */
    @ApiModelProperty(value = "超级管理员   0：否   1：是")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean superAdmin;
    /**
     * todo 改成最多可同时登录多少个终端
     */
    @ApiModelProperty(value = "是否可以同时登录多个终端   0：否   1：是")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean multipleLogin;
    /**
     * 创建者
     */
    private String creator;
    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdList;

    @ApiModelProperty(value = "部门ID列表")
    private List<Long> deptIds;

}