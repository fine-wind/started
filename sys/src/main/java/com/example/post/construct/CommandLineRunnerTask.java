package com.example.post.construct;

import com.example.common.asyn.CachedThreadPool;
import com.example.modules.param.service.SysParamsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 启动后运行数据
 */
@Service
public class CommandLineRunnerTask implements CommandLineRunner {

    final SysParamsService paramsService;

    public CommandLineRunnerTask(SysParamsService paramsService) {
        this.paramsService = paramsService;
    }

    /**
     * 重新清理配置缓存
     */
    @Override
    public void run(String... args) throws Exception {
        CachedThreadPool.execute(paramsService::clear, 3, TimeUnit.SECONDS);
    }
}
