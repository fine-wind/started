package com.example.started.api.modules.job.controller;

import com.example.started.api.modules.job.dto.ScheduleJobDTO;
import com.example.started.api.modules.job.bo.ScheduleJobBo;
import com.example.started.api.modules.job.service.ScheduleJobService;
import com.example.started.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.Result;
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

/**
 * 定时任务
 *
 *
 */
@RestController
@RequestMapping("/sys/schedule")
@Api(tags="定时任务")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	@PostMapping("page")
	@ApiOperation("分页")
	@ApiImplicitParams({
		@ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = "beanName", value = "beanName", paramType = "query", dataType="String")
	})

	public Result<PageData<ScheduleJobDTO>> page(@RequestBody ScheduleJobBo params){
		PageData<ScheduleJobDTO> page = scheduleJobService.page(params);

		return new Result<PageData<ScheduleJobDTO>>().ok(page);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")

	public Result<ScheduleJobDTO> info(@PathVariable("id") Long id){
		ScheduleJobDTO schedule = scheduleJobService.get(id);

		return new Result<ScheduleJobDTO>().ok(schedule);
	}

	@PostMapping
	@ApiOperation("保存")
	@LogOperation("保存")

	public Result<?> save(@RequestBody ScheduleJobDTO dto){
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		scheduleJobService.save(dto);

		return new Result<>();
	}

	@PutMapping
	@ApiOperation("修改")
	@LogOperation("修改")

	public Result<?> update(@RequestBody ScheduleJobDTO dto){
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		scheduleJobService.update(dto);

		return new Result<>();
	}

	@DeleteMapping
	@ApiOperation("删除")
	@LogOperation("删除")

	public Result<?> delete(@RequestBody Long[] ids){
		scheduleJobService.deleteBatch(ids);

		return new Result<>();
	}

	@PutMapping("/run")
	@ApiOperation("立即执行")
	@LogOperation("立即执行")

	public Result<?> run(@RequestBody Long[] ids){
		scheduleJobService.run(ids);

		return new Result<>();
	}

	@PutMapping("/pause")
	@ApiOperation("暂停")
	@LogOperation("暂停")

	public Result<?> pause(@RequestBody Long[] ids){
		scheduleJobService.pause(ids);

		return new Result<>();
	}

	@PutMapping("/resume")
	@ApiOperation("恢复")
	@LogOperation("恢复")

	public Result<?> resume(@RequestBody Long[] ids){
		scheduleJobService.resume(ids);

		return new Result<>();
	}

}
