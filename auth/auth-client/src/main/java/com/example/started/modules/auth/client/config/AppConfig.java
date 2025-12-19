package com.example.started.modules.auth.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    /**
     * 应用访问路径
     * <a href="http://example.com">demo 1</a>
     * <a href="https://example.com">demo 2</a>
     */
    private String domain;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 登录中心地址
     */
    private String authLoginUrl;
}
