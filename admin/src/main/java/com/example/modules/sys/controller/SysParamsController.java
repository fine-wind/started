package com.example.modules.sys.controller;

import com.example.common.annotation.LogOperation;
import com.example.common.constant.Constant;
import com.example.common.data.page.PageData;
import com.example.common.utils.Result;
import com.example.common.validator.AssertUtils;
import com.example.common.validator.ValidatorUtils;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.DefaultGroup;
import com.example.common.validator.group.UpdateGroup;
import com.example.modules.param.bo.SysParamsBo;
import com.example.modules.param.dto.SysParamsDTO;
import com.example.modules.param.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 参数管理
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/params")
@Api(tags = "参数管理")
public class SysParamsController {
    @Autowired
    private SysParamsService sysParamsService;

    @PostMapping("/page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "paramCode", value = "参数编码", paramType = "query", dataType = "String")
    })
    public Result<PageData<SysParamsDTO>> page(@RequestBody SysParamsBo params) {
        PageData<SysParamsDTO> page = sysParamsService.page(params);

        return new Result<PageData<SysParamsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<SysParamsDTO> get(@PathVariable("id") Long id) {
        SysParamsDTO data = sysParamsService.get(id);

        return new Result<SysParamsDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result save(@RequestBody SysParamsDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysParamsService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    public Result update(@RequestBody SysParamsDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysParamsService.update(dto);

        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysParamsService.delete(ids);

        return new Result<>();
    }

    @PutMapping("/clear")
    @ApiOperation("清除缓存")
    @LogOperation("清除缓存")
    public Result<?> clear() {
        sysParamsService.clear();
        return new Result<>();
    }

}
