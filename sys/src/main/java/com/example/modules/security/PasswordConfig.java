package com.example.modules.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * 密码配置策略
 */
@Configuration
public class PasswordConfig {

    private static SCryptPasswordEncoder passwordEncoder;


    @Bean
    public SCryptPasswordEncoder bean() {
        return passwordEncoder = new SCryptPasswordEncoder();
    }

    /**
     * 加密
     *
     * @param password 待加密密码
     * @return 加密后密码
     */
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 校验
     *
     * @param rawPassword     未加密密码
     * @param encodedPassword 已加密密码
     * @return 是否一致
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}