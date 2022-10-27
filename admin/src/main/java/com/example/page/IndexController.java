package com.example.page;

import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_HOST;
import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_NAME;

@RestController
public class IndexController {
    static long startTime = System.currentTimeMillis();

    @RequestMapping("/")
    public static Result<?> index() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("startTime", DateUtil.toString(new Date(startTime)));
        map.put("BindingDomainName", THIS_HOST.getValue());
        map.put("BindingName", THIS_NAME.getValue());
        // todo 初始化操作
        map.put("init", Boolean.FALSE);
        return new Result<>().ok(map);
    }

}
