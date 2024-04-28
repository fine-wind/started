package com.example.started.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.DateUtil;
import com.example.common.v1.base.service.impl.BaseServiceV1Impl;
import com.example.started.plan.bo.PlanEventBo;
import com.example.started.plan.dao.PlanEventDao;
import com.example.started.plan.dto.PlanEventDto;
import com.example.started.plan.entity.PlanEventEntity;
import com.example.started.plan.service.PlanDayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @since 1.0.0
 */
@Service
public class PlanEventServiceImpl extends BaseServiceV1Impl<PlanEventBo, PlanEventDao, PlanEventEntity> implements PlanDayService {

    public LambdaQueryWrapper<PlanEventEntity> getWrapper(PlanEventBo params) {
        return super.getQueryWrapper(params)
                .eq(Objects.nonNull(params.getDt()), PlanEventEntity::getDt, DateUtil.dateToStr(params.getDt()))
                .orderByDesc(PlanEventEntity::getSort)
                ;
    }

    @Override
    public void save(PlanEventDto bo) {
        PlanEventEntity entity = ConvertUtils.sourceToTarget(bo, PlanEventEntity.class);
        this.insert(entity);
    }

    @Override
    public List<PlanEventDto> getOneDay() {
        PlanEventBo params = new PlanEventBo();
        LambdaQueryWrapper<PlanEventEntity> wrapper = this.getWrapper(params);
        wrapper.ge(PlanEventEntity::getDt, DateUtil.getLastDate(12));
        wrapper.le(PlanEventEntity::getDt, DateUtil.getLastDate(-12));
        List<PlanEventEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list, PlanEventDto.class);
    }

    @Override
    public List<PlanEventDto> getOneDay(Date day) {
        if (Objects.isNull(day)) {
            return List.of();
        }
        PlanEventBo params = new PlanEventBo();
        params.setDt(day);
        List<PlanEventEntity> list = baseDao.selectList(this.getWrapper(params));
        return ConvertUtils.sourceToTarget(list, PlanEventDto.class);
    }

    @Override
    public void task() {

    }

}
