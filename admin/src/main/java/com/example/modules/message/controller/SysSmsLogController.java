package com.example.modules.message.controller;

import com.example.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.Result;
import com.example.modules.message.bo.SysSmsLogBo;
import com.example.modules.message.dto.SysSmsLogDTO;
import com.example.modules.message.service.SysSmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * 短信日志
 */
@RestController
@RequestMapping("sys/smslog")
@Api(tags = "短信日志")
public class SysSmsLogController {
    @Autowired
    private SysSmsLogService sysSmsLogService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })

    public Result<PageData<SysSmsLogDTO>> page(@ApiIgnore @RequestParam SysSmsLogBo params) {
        PageData<SysSmsLogDTO> page = sysSmsLogService.selectPage(params);

        return new Result<PageData<SysSmsLogDTO>>().ok(page);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@RequestBody Long[] ids) {
        sysSmsLogService.deleteBatchIds(Arrays.asList(ids));

        return new Result<>();
    }
}
