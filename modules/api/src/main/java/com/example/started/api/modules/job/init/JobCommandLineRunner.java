package com.example.started.api.modules.job.init;

import com.example.started.api.modules.job.dao.ScheduleJobDao;
import com.example.started.api.modules.job.entity.ScheduleJobEntity;
import com.example.started.api.modules.job.utils.ScheduleUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化定时任务数据
 */
@Log4j2
@Component
public class JobCommandLineRunner implements CommandLineRunner {
    private final Scheduler scheduler;
    private final ScheduleJobDao scheduleJobDao;

    @Autowired
    public JobCommandLineRunner(Scheduler scheduler, ScheduleJobDao scheduleJobDao) {
        this.scheduler = scheduler;
        this.scheduleJobDao = scheduleJobDao;
    }

    @Override
    public void run(String... args) {
        log.trace("开始加载定时任务");
        new Thread(() -> {
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
            log.trace("定时任务加载完毕");
        }).start();
    }
}