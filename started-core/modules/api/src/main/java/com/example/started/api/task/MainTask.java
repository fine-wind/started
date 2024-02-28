package com.example.started.api.task;

import com.example.started.api.modules.param.service.SysParamsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service("com.example.started.api.task.MainTask")
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
        new Thread(paramsService::clear).start();
    }
}
