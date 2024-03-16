package com.example.started.auth;

import com.example.common.v0.data.dto.LogInRegisterDTO;

import java.util.List;

public interface LoginService {

    void join(LogInRegisterDTO mobile);

    /**
     * 记录一次尝试登录
     *
     * @param username username
     */
    long login(String username);
}
