package com.example.started.auth.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * todo 做成依赖此包就能自动扫描本包的class
 */
@Component
@ComponentScan(basePackages = "com.example.started.auth.client")
public class Config {
}
