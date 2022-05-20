package com.example.modules.job.init;

import com.example.admin.job.entity.ScheduleJobEntity;
import com.example.common.asyn.CachedThreadPool;
import com.example.modules.job.dao.ScheduleJobDao;
import com.example.modules.job.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 初始化定时任务数据
 */
@Component
public class JobCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Override
    public void run(String... args) {
        CachedThreadPool.execute(() -> {
            List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.selectList(null);
            for (ScheduleJobEntity scheduleJob : scheduleJobList) {
                CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
                //如果不存在，则创建
                if (cronTrigger == null) {
                    ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
                } else {
                    ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                }
            }
        }, 3, TimeUnit.SECONDS);
    }
}
