package com.example.started.config;

import com.example.common.v0.constant.Constant;
import org.springframework.stereotype.Component;

@Component
public class WhiteListConfAuth implements WhiteListConf {


    @Override
    public String[] whiteList() {
        return new String[]{"/", "/captcha.png", Constant.User.JOIN, Constant.User.LOGIN};
    }
}
