package com.example.started.auth;

import com.example.common.v0.data.dto.LogInRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Override
    public void join(LogInRegisterDTO mobile) {

    }

    @Override
    public long login(String username) {
        return 0;
    }
}
