package com.example.started.plan.service.impl;

import com.example.started.common.v0.utils.ConvertUtils;
import com.example.started.common.v0.utils.DateUtil;
import com.example.started.plan.dao.PlanEventDao;
import com.example.started.plan.dto.PlanEventDto;
import com.example.started.plan.entity.PlanEventEntity;
import com.example.started.plan.service.PlanDayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class PlanEventServiceImpl implements PlanDayService {

    final PlanEventDao planEventDao;


    @Override
    public void save(PlanEventDto bo) {
        PlanEventEntity entity = ConvertUtils.sourceToTarget(bo, PlanEventEntity.class);
        planEventDao.save(entity);
    }

    @Override
    public List<PlanEventDto> getOneDay() {
        List<PlanEventEntity> list = planEventDao.findByDtBetween(DateUtil.getLastDate(12), DateUtil.getLastDate(-12));
        return ConvertUtils.sourceToTarget(list, PlanEventDto.class);
    }

    @Override
    public List<PlanEventDto> getOneDay(Date day) {
        if (Objects.isNull(day)) {
            return List.of();
        }
        List<PlanEventEntity> list = planEventDao.findByDtEquals(day);
        return ConvertUtils.sourceToTarget(list, PlanEventDto.class);
    }

    @Override
    public void task() {

    }

}
