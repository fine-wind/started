package com.example.started.api.config;

import com.example.common.v0.constant.Constant;
import com.example.started.auth.client.conf.WhiteListConf;
import org.springframework.stereotype.Component;

@Component
public class WhiteListConfAuth implements WhiteListConf {


    @Override
    public String[] whiteList() {
        return new String[]{"/", "/captcha.png", Constant.User.JOIN, Constant.User.LOGIN};
    }
}
