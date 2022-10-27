package com.example.modules.sys.user;

import com.example.common.v0.annotation.DictTranslationField;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * 用于显示用户信息及其他
 */
@Data
public class UserVo {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "{sysuser.username.require}", groups = DefaultGroup.class)
    private String username;

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
    @Range(min = 0, max = 2, message = "{sysuser.gender.range}", groups = DefaultGroup.class)
    private Integer gender;

    @ApiModelProperty(value = "部门ID", required = true)
    @DictTranslationField(table = "sys_group", column = "name", key = "id")
    private Long deptId;

    @ApiModelProperty(value = "状态  0：停用    1：正常", required = true)
    @Range(min = 0, max = 1, message = "{sysuser.status.range}", groups = DefaultGroup.class)
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

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdList;
}
