package com.example.started.modules.auth.server.config;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    public String encode(CharSequence rawPassword) {
        // 可以使用BCrypt或其他加密方式
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(11));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
}