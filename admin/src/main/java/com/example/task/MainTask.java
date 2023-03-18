package com.example.task;

import com.example.common.v0.asyn.CachedThreadPool;
import com.example.modules.param.service.SysParamsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MainTask implements CommandLineRunner {

    final SysParamsService paramsService;

    public MainTask(SysParamsService paramsService) {
        this.paramsService = paramsService;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        // todo 做点什么。。。
        CachedThreadPool.execute(paramsService::clear, 3, TimeUnit.SECONDS);
    }
}
