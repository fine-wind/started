package com.example;

import com.example.common.v0.utils.BeanUtils;
import com.example.common.v0.utils.to.BeanDiff;
import com.example.modules.sys.user.v1.dto.UserDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.ArrayList;

/**
 * started
 * <p>
 * 2020/2/4 16:10
 *
 * @author xing xingii@outlook.com
 */
@SpringBootApplication()
@EnableScheduling // 开启对计划任务的支持
@EnableAsync // 启用异步
@EnableMethodSecurity
public class AdminApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApplication.class);
    }

}
