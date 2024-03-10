package com.example.started.task;

import com.example.started.modules.param.service.SysParamsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
        new Thread(paramsService::reload).start();
    }
}
