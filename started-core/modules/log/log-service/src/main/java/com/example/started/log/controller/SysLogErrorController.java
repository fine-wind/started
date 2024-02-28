package com.example.started.log.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.Result;
import com.example.started.log.bo.SysLogErrorBo;
import com.example.started.log.dto.SysLogErrorDTO;
import com.example.started.log.service.SysLogErrorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 异常日志
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/error")
@Api(tags = "异常日志")
public class SysLogErrorController {
    @Autowired
    private SysLogErrorService sysLogErrorService;

    @PostMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })

    public Result<PageData<SysLogErrorDTO>> page(@RequestBody SysLogErrorBo params) {
        PageData<SysLogErrorDTO> page = sysLogErrorService.page(params);

        return Result.ok(page);
    }

}
