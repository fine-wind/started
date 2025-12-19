package com.example.started.modules.auth.server.login;

import lombok.Data;

@Data
public class LoginBo {
    private String appId;
    private String uuid;
    private String username;
    private String password;
    /**
     * 记住我
     */
    private Boolean rememberMe;
}
