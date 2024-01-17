package com.example.started.api.modules.sys.controller;

import com.example.started.api.modules.sys.dto.SysRegionDTO;
import com.example.started.api.modules.sys.dto.region.RegionProvince;
import com.example.started.api.modules.sys.service.SysRegionService;
import com.example.started.api.modules.sys.bo.SysRegionBo;
import com.example.started.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.AssertUtils;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 */
@RestController
@RequestMapping("/sys/region")
@Api(tags = "行政区域")
public class SysRegionController {
    @Autowired
    private SysRegionService sysRegionService;

    @PostMapping("list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上级ID", paramType = "query", dataType = "String")
    })
    public Result<List<SysRegionDTO>> list(@RequestBody SysRegionBo params) {
        List<SysRegionDTO> list = sysRegionService.list(params);

        return new Result<List<SysRegionDTO>>().ok(list);
    }

    @GetMapping("tree")
    @ApiOperation("树形数据")
    public Result<List<Map<String, Object>>> tree() {
        List<Map<String, Object>> list = sysRegionService.getTreeList();

        return new Result<List<Map<String, Object>>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")

    public Result<SysRegionDTO> get(@PathVariable("id") Long id) {
        SysRegionDTO data = sysRegionService.get(id);

        return new Result<SysRegionDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result<?> save(@RequestBody SysRegionDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysRegionService.save(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result<?> update(@RequestBody SysRegionDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysRegionService.update(dto);

        return new Result<>();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@PathVariable("id") Long id) {
        //效验数据
        AssertUtils.isNull(id, "id");

        Long count = sysRegionService.getCountByPid(id);
        if (count > 0) {
            throw new ServerException(Constant.UniversalCode.FORBIDDEN, "存在子节点未删除");
        }

        sysRegionService.delete(id);

        return new Result<>();
    }

    @GetMapping("region")
    @ApiOperation("地区列表")
    @ApiImplicitParam(name = "threeLevel", value = "是否显示3级   true显示   false不显示", paramType = "query", dataType = "boolean")
    public Result<List<RegionProvince>> region(@RequestParam(value = "threeLevel", defaultValue = "true") boolean threeLevel) {
        List<RegionProvince> list = sysRegionService.getRegion(threeLevel);

        return new Result<List<RegionProvince>>().ok(list);
    }

}
