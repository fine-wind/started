package com.example.started;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * started
 * <p>
 * 2020/2/4 16:10
 *
 * @author xing xingii@outlook.com
 */
@SpringBootApplication(scanBasePackages = {"com.example.started"})
@EnableScheduling // 开启对计划任务的支持
@EnableAsync // 启用异步
public class AuthServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthServerApplication.class);
    }

}
