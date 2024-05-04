package com.example.started.task;

import com.example.started.modules.job.init.JobCommandLineRunner;
import com.example.started.modules.param.service.SysParamsService;
import com.example.started.modules.sys.service.impl.SysMenuServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MainTask implements CommandLineRunner {

    final SysParamsService paramsService;
    final JobCommandLineRunner jobCommandLineRunner;
    final SysMenuServiceImpl sysMenuService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        Thread.startVirtualThread(paramsService::reload);
        Thread.startVirtualThread(jobCommandLineRunner::run);
        Thread.startVirtualThread(sysMenuService::run);
    }
}
