package com.example.started;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/config")
public class TestController {
    @Value("${my.config.property:default}")
    private String configProperty;

    @GetMapping("")
    public String getConfig() {
        return configProperty;
    }
}
