package com.example.started.plan.controller;

import com.example.started.common.v0.utils.DateUtil;
import com.example.started.common.v0.utils.Result;
import com.example.started.plan.dto.PlanEventDto;
import com.example.started.plan.service.PlanDayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * v
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/plan")
@AllArgsConstructor
public class PlanController {

    private final PlanDayService planDayService;

    /**
     * 某天的数据
     *
     * @return
     */
    @GetMapping("/init")
    public Result<List<PlanEventDto>> get() {
        List<PlanEventDto> data = planDayService.getOneDay();
        return Result.ok(data);
    }

    /**
     * 某天的数据
     *
     * @param day
     * @return
     */
    // @PreAuthorize("@se.hasRole('plan.day')")
    @GetMapping("/{day}")
    public Result<List<PlanEventDto>> get(@PathVariable String day) {
        List<PlanEventDto> data = planDayService.getOneDay(DateUtil.strToDate(day));
        return Result.ok(data);
    }

    @PostMapping("/save")
    public Result<List<PlanEventDto>> save(@RequestBody PlanEventDto params) {
        planDayService.save(params);
        return this.get(DateUtil.dateToStr(params.getDt()));
    }

    @PostMapping("/del")
    public Result<List<PlanEventDto>> del(@RequestBody PlanEventDto params) {
        planDayService.save(params);
        return this.get(DateUtil.dateToStr(params.getDt()));
    }

}
