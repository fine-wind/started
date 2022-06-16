package com.example.page;

import com.example.common.utils.DateUtil;
import com.example.common.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.common.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_HOST;
import static com.example.common.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_NAME;

@RestController
public class IndexController {
    static long startTime = System.currentTimeMillis();

    @RequestMapping("/")
    public static Result<?> index() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("startTime", DateUtil.toString(new Date(startTime)));
        map.put("BindingDomainName", THIS_HOST.getValue());
        map.put("BindingName", THIS_NAME.getValue());
        return new Result<>().ok(map);
    }

}
