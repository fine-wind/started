package com.example.modules.sys.controller;

import com.example.modules.sys.user.bo.UserBo;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.service.UserService;
import com.example.common.utils.*;
import com.example.common.annotation.LogOperation;
import com.example.common.constant.Constant;
import com.example.common.exception.UniversalCode;
import com.example.common.data.page.PageData;
import com.example.common.validator.AssertUtils;
import com.example.common.validator.ValidatorUtils;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.DefaultGroup;
import com.example.common.validator.group.UpdateGroup;
import com.example.common.data.modules.role.SysRoleUserService;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.dto.PasswordDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理")
public class SysUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @PostMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType = "String")
    })

    public Result<PageData<UserDto>> page(@RequestBody UserBo params) {
        PageData<UserDto> page = userService.selectPage(params);

        return new Result<PageData<UserDto>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")

    public Result<UserDto> get(@PathVariable("id") Long id) {
        UserDto data = userService.getUserByUserId(id);
        //用户角色列表
        List<Long> roleIdList = sysRoleUserService.getRoleIdList(id);
        data.setRoleIdList(roleIdList);

        return new Result<UserDto>().ok(data);
    }

    @GetMapping("info")
    @ApiOperation("登录用户信息")
    public Result<UserDto> info() {
        UserDto data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), UserDto.class);
        return new Result<UserDto>().ok(data);
    }

    @PutMapping("password")
    @ApiOperation("修改密码")
    @LogOperation("修改密码")
    public Result<?> password(@RequestBody PasswordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto);

        SecurityUserDetails user = SecurityUser.getUser();

        //原密码不正确
        boolean b = userService.updatePassword(user.getId(), dto.getPassword(), dto.getNewPassword());
        if (!b) {
            return new Result<>().error(UniversalCode.PASSWORD_ERROR, "原密码不正确");
        }
        return new Result<>();
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")

    public Result save(@RequestBody UserDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result update(@RequestBody UserDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userService.updateUserInfo(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        userService.deleteBatchIds(Arrays.asList(ids));

        return new Result();
    }

}
