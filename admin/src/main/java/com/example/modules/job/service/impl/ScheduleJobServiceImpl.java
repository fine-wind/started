package com.example.modules.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.admin.job.entity.ScheduleJobEntity;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.job.bo.ScheduleJobBo;
import com.example.modules.job.dao.ScheduleJobDao;
import com.example.modules.job.dto.ScheduleJobDTO;
import com.example.modules.job.service.ScheduleJobService;
import com.example.modules.job.utils.ScheduleUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobBo, ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;

    @Override
    public PageData<ScheduleJobDTO> page(ScheduleJobBo params) {
        IPage<ScheduleJobEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getWrapper(JSON.parseObject(JSON.toJSONString(params), ScheduleJobEntity.class))
        );
        return getPageData(page, ScheduleJobDTO.class);
    }

    @Override
    public ScheduleJobDTO get(Long id) {
        ScheduleJobEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, ScheduleJobDTO.class);
    }

    public QueryWrapper<ScheduleJobEntity> getWrapper(ScheduleJobEntity params) {
        String beanName = params.getBeanName();

        QueryWrapper<ScheduleJobEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(beanName), "bean_name", beanName);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ScheduleJobDTO dto) {
        ScheduleJobEntity entity = ConvertUtils.sourceToTarget(dto, ScheduleJobEntity.class);

        entity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.insert(entity);

        ScheduleUtils.createScheduleJob(scheduler, entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobDTO dto) {
        ScheduleJobEntity entity = ConvertUtils.sourceToTarget(dto, ScheduleJobEntity.class);

        ScheduleUtils.updateScheduleJob(scheduler, entity);

        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            ScheduleUtils.deleteScheduleJob(scheduler, id);
        }

        //删除数据
        this.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 批量更新状态
     *
     * @param ids    多个id
     * @param status 状态
     * @return 更新的个数
     */
    @Override
    @Transactional
    public int updateBatch(Long[] ids, int status) {
        for (Long id : ids) {
            ScheduleJobEntity entity = new ScheduleJobEntity();
            entity.setId(id);
            entity.setStatus(status);
            baseDao.updateById(entity);
        }
        return ids.length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] ids) {
        for (Long id : ids) {
            ScheduleUtils.run(scheduler, this.selectById(id));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] ids) {
        for (Long id : ids) {
            ScheduleUtils.pauseJob(scheduler, id);
        }

        updateBatch(ids, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] ids) {
        for (Long id : ids) {
            ScheduleUtils.resumeJob(scheduler, id);
        }

        updateBatch(ids, Constant.ScheduleStatus.NORMAL.getValue());
    }

}
