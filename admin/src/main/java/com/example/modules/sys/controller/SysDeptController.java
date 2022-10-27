package com.example.modules.sys.controller;

import com.example.common.v0.annotation.LogOperation;
import com.example.common.v0.data.bo.SysDeptBo;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.AssertUtils;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.example.modules.sys.dept.dto.SysDeptDTO;
import com.example.modules.sys.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("/sys/dept")
@Api(tags = "部门管理")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("list")
    @ApiOperation("列表")
    public Result<List<SysDeptDTO>> list(@RequestBody SysDeptBo bo) {
        List<SysDeptDTO> list = sysDeptService.list(bo);

        return new Result<List<SysDeptDTO>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<SysDeptDTO> get(@PathVariable("id") Long id) {
        SysDeptDTO data = sysDeptService.get(id);

        return new Result<SysDeptDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result<?> save(@RequestBody SysDeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysDeptService.save(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result<?> update(@RequestBody SysDeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysDeptService.update(dto);

        return new Result<>();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@PathVariable("id") Long id) {
        //效验数据
        AssertUtils.isNull(id, "id");

        sysDeptService.delete(id);

        return new Result<>();
    }

}
