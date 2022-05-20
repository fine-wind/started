package com.example.page;

import com.example.common.utils.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.example.common.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_HOST;
import static com.example.common.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_NAME;

@RestController
public class IndexController {
    static long startTime = System.currentTimeMillis();

    @RequestMapping("/")
    public static String index() {

        return "^_^" +
                "<br/>启动时间：" + DateUtil.toString(new Date(startTime)) +
                "<br/>绑定域名：" + THIS_HOST.getValue() +
                "<br/>绑定名称：" + THIS_NAME.getValue();
    }

}
