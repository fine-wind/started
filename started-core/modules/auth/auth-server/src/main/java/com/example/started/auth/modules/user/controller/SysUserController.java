package com.example.started.auth.modules.user.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.bo.PageBo;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.AssertUtils;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.example.started.auth.modules.role.SysRoleUserService;
import com.example.started.auth.modules.user.v1.dto.UserDto;
import com.example.started.auth.modules.user.v1.service.UserServiceV1;
import com.example.started.auth.client.user.SecurityUser;
import com.example.started.auth.modules.sys.dto.PasswordDTO;
import com.example.started.common.v0.annotation.LogOperation;
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
    private final UserServiceV1 userService;
    private final SysRoleUserService sysRoleUserService;

    @Autowired
    public SysUserController(UserServiceV1 userService, SysRoleUserService sysRoleUserService) {
        this.userService = userService;
        this.sysRoleUserService = sysRoleUserService;
    }

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
    public Result<PageData<UserDto>> page(@RequestBody UserDto params, PageBo page) {
        PageData<UserDto> pageData = userService.selectPage(params, page);

        return Result.ok(pageData);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<UserDto> get(@PathVariable("id") String id) {
        UserDto data = userService.getUserByUserId(id);
        //用户角色列表
        List<Long> roleIdList = sysRoleUserService.getRoleIdList(id);
        data.setRoleIdList(roleIdList);

        return Result.ok(data);
    }

    @GetMapping("info")
    @ApiOperation("登录用户信息")
    public Result<UserDto> info() {
        UserDto data = ConvertUtils.sourceToTarget(SecurityUser.getUserName(), UserDto.class);
        return Result.ok(data);
    }

    @PutMapping("password")
    @ApiOperation("修改密码")
    @LogOperation("修改密码")
    public Result<?> password(@RequestBody PasswordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto);

        //原密码不正确
        boolean b = userService.updatePassword(SecurityUser.getUserId(), dto.getPassword(), dto.getNewPassword());
        if (!b) {
            return Result.error(UniversalCode.PASSWORD_ERROR, "原密码不正确");
        }
        return new Result<>();
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result<?> save(@RequestBody UserDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.insert(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result<?> update(@RequestBody UserDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userService.updateUserInfo(dto);

        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        userService.deleteBatchIds(Arrays.asList(ids));

        return new Result<>();
    }

}
