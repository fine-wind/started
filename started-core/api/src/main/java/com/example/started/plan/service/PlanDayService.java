package com.example.started.plan.service;

import com.example.started.plan.dto.PlanEventDto;

import java.util.Date;
import java.util.List;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
public interface PlanDayService  {

    void save(PlanEventDto bo);

    List<PlanEventDto> getOneDay();

    /**
     * @param day 某天
     * @return 获取一天的事件
     */
    List<PlanEventDto> getOneDay(Date day);

    /**
     * todo 生成明天的任务及计划
     */
    void task();
}
