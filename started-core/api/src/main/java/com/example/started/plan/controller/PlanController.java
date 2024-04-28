package com.example.started.plan.controller;

import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.Result;
import com.example.started.plan.dto.PlanEventDto;
import com.example.started.plan.service.PlanDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Api(tags = "计划")
@RestController
@RequestMapping("/plan")
@AllArgsConstructor
public class PlanController {

    private final PlanDayService planDayService;

    //    @PreAuthorize("@se.hasRole('plan.init')")
    @GetMapping("/init")
    @ApiOperation("某天的数据")
    public Result<List<PlanEventDto>> get() {
        List<PlanEventDto> data = planDayService.getOneDay();
        return Result.ok(data);
    }

    // @PreAuthorize("@se.hasRole('plan.day')")
    @GetMapping("/{day}")
    @ApiOperation("某天的数据")
    public Result<List<PlanEventDto>> get(@PathVariable String day) {
        List<PlanEventDto> data = planDayService.getOneDay(DateUtil.strToDate(day));
        return Result.ok(data);
    }

    @PreAuthorize("@se.hasRole('plan.save')")
    @PostMapping("/save")
    public Result<List<PlanEventDto>> save(@RequestBody PlanEventDto params) {
        planDayService.save(params);
        return this.get(DateUtil.dateToStr(params.getDt()));
    }

    @PreAuthorize("@se.hasRole('plan.del')")
    @PostMapping("/del")
    public Result<List<PlanEventDto>> del(@RequestBody PlanEventDto params) {
        planDayService.save(params);
        return this.get(DateUtil.dateToStr(params.getDt()));
    }

}
