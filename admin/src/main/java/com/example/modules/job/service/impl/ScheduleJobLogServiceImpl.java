package com.example.modules.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.job.entity.ScheduleJobLogEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.job.bo.ScheduleJobLogBo;
import com.example.modules.job.dao.ScheduleJobLogDao;
import com.example.modules.job.dto.ScheduleJobLogDTO;
import com.example.modules.job.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogBo, ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public PageData<ScheduleJobLogDTO> page(ScheduleJobLogBo params) {

        IPage<ScheduleJobLogEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getWrapper(JSON.parseObject(JSON.toJSONString(params), ScheduleJobLogEntity.class))
        );
        return getPageData(page, ScheduleJobLogDTO.class);
    }

    public QueryWrapper<ScheduleJobLogEntity> getWrapper(ScheduleJobLogEntity params) {
        Long jobId = params.getJobId();

        QueryWrapper<ScheduleJobLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(jobId != null, "job_id", jobId);

        return wrapper;
    }


    @Override
    public ScheduleJobLogDTO get(Long id) {
        ScheduleJobLogEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, ScheduleJobLogDTO.class);
    }

}
