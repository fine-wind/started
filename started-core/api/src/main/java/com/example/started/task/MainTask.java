package com.example.started.task;

import com.example.started.modules.param.service.SysParamsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MainTask implements CommandLineRunner {

    final SysParamsService paramsService;

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
