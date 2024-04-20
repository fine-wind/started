package com.example.started.plan.service;

import com.example.common.v1.base.service.BaseServiceV1;
import com.example.started.plan.bo.PlanEventBo;
import com.example.started.plan.dto.PlanEventDto;
import com.example.started.plan.entity.PlanEventEntity;

import java.util.Date;
import java.util.List;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
public interface PlanDayService extends BaseServiceV1<PlanEventBo, PlanEventEntity> {

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
