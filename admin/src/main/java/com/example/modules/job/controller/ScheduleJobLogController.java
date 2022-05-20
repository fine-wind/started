package com.example.modules.job.controller;

import com.example.admin.job.bo.ScheduleJobLogBo;
import com.example.common.constant.Constant;
import com.example.common.data.page.PageData;
import com.example.common.utils.Result;
import com.example.modules.job.dto.ScheduleJobLogDTO;
import com.example.modules.job.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 定时任务日志
 *
 *
 */
@RestController
@RequestMapping("/sys/scheduleLog")
@Api(tags="定时任务日志")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	@GetMapping("page")
	@ApiOperation("分页")
	@ApiImplicitParams({
		@ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = "jobId", value = "jobId", paramType = "query", dataType="String")
	})

	public Result<PageData<ScheduleJobLogDTO>> page(@ApiIgnore @RequestParam ScheduleJobLogBo params){
		PageData<ScheduleJobLogDTO> page = scheduleJobLogService.page(params);

		return new Result<PageData<ScheduleJobLogDTO>>().ok(page);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")

	public Result<ScheduleJobLogDTO> info(@PathVariable("id") Long id){
		ScheduleJobLogDTO log = scheduleJobLogService.get(id);

		return new Result<ScheduleJobLogDTO>().ok(log);
	}
}
