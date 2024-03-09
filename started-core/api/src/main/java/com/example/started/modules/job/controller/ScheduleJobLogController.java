package com.example.started.modules.job.controller;

import com.example.started.modules.job.bo.ScheduleJobLogBo;
import com.example.started.modules.job.dto.ScheduleJobLogDTO;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.Result;
import com.example.started.modules.job.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 定时任务日志
 */
@RestController
@RequestMapping("/sys/scheduleLog")
@Api(tags = "定时任务日志")
@AllArgsConstructor
public class ScheduleJobLogController {
    private ScheduleJobLogService scheduleJobLogService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "jobId", value = "jobId", paramType = "query", dataType = "String")
    })

    public Result<PageData<ScheduleJobLogDTO>> page(@ApiIgnore @RequestParam ScheduleJobLogBo params) {
        PageData<ScheduleJobLogDTO> page = scheduleJobLogService.page(params);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<ScheduleJobLogDTO> info(@PathVariable("id") Long id) {
        ScheduleJobLogDTO log = scheduleJobLogService.get(id);

        return Result.ok(log);
    }
}
