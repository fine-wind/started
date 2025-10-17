package com.example.started.config;

import com.example.started.common.constant.Constant;
import org.springframework.stereotype.Component;

@Component
public class WhiteListConfAuth implements WhiteListConf {


    @Override
    public String[] whiteList() {
        return new String[]{"/", "/captcha.png", Constant.User.JOIN, Constant.User.LOGIN};
    }
}
