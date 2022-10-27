package com.example.modules.sys.controller;

import com.example.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.AssertUtils;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.modules.security.service.ShiroService;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.security.vo.SysMenuVo;
import com.example.modules.sys.bo.SysMenuBo;
import com.example.modules.sys.dto.SysMenuDTO;
import com.example.modules.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/sys/menu")
@Api(tags = "菜单管理")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    @GetMapping("/nav")
    @ApiOperation("导航")
    public Result<?> nav() {
        SecurityUserDetails user = SecurityUser.getUser();
        if (Objects.isNull(user)) {
            return new Result<>().error(Constant.UniversalCode.UNAUTHORIZED, "登录失效");
        }
        List<SysMenuDTO> list = sysMenuService.getUserMenuTree(user, Constant.RESOURCES.MENU.getValue());

        return new Result<List<SysMenuDTO>>().ok(list);
    }

    @GetMapping("permissions")
    @ApiOperation("权限标识")
    public Result<Map<String, SysMenuVo>> permissions() {
        SecurityUserDetails user = SecurityUser.getUser();
        Map<String, SysMenuVo> set = shiroService.getUserPermissions(user);

        return new Result<Map<String, SysMenuVo>>().ok(set);
    }

    @PostMapping("tree")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型", paramType = "query", dataType = "int")
    public Result<List<SysMenuDTO>> tree(@RequestBody SysMenuBo bo) {
        List<SysMenuDTO> list = sysMenuService.getAllMenuTree(bo.getType());

        return new Result<List<SysMenuDTO>>().ok(list);
    }

    @PostMapping("list")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    public Result<List<SysMenuDTO>> list(@RequestBody SysMenuBo bo) {
        List<SysMenuDTO> list = sysMenuService.getMenuList(SecurityUser.getUser(), bo);

        return new Result<List<SysMenuDTO>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")

    public Result<SysMenuDTO> get(@PathVariable("id") Long id) {
        SysMenuDTO data = sysMenuService.get(id);

        return new Result<SysMenuDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")

    public Result<?> save(@RequestBody SysMenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysMenuService.save(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result<?> update(@RequestBody SysMenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysMenuService.update(dto);

        return new Result<>();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@PathVariable("id") Long id) {
        //效验数据
        AssertUtils.isNull(id, "id");

        //判断是否有子菜单或按钮
        List<SysMenuDTO> list = sysMenuService.getListPid(id);
        if (list.size() > 0) {
            return new Result<>().error(Constant.UniversalCode.UNPROCESSABLE_ENTITY, "存在子菜单或按钮");
        }

        sysMenuService.delete(id);

        return new Result<>();
    }

    @GetMapping("select")
    @ApiOperation("角色菜单权限")

    public Result<List<SysMenuDTO>> select() {
        SecurityUserDetails user = SecurityUser.getUser();
        List<SysMenuDTO> list = sysMenuService.getUserMenuTree(user, null);

        return new Result<List<SysMenuDTO>>().ok(list);
    }
}
