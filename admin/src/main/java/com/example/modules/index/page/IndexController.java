package com.example.modules.index.page;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.Result;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

@RestController
public class IndexController {
    static long startTime = System.currentTimeMillis();
    private final UserServiceV1 userServiceV1;
    private static boolean init = true;

    @Autowired
    public IndexController(UserServiceV1 userServiceV1) {
        this.userServiceV1 = userServiceV1;
    }

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Index> index() {
        Index index = new Index();
        index.setStartTime(DateUtil.toString(new Date(startTime)));
        index.setBindingDomainName(THIS_HOST.getValue());
        index.setName(THIS_NAME.getValue());
        index.setShortName(THIS_SHORT_NAME.getValue());
        index.setCopyright(COPYRIGHT.getValue());
        index.setCaptcha(Boolean.parseBoolean(CAPTCHA.getValue()));
        init = init && userServiceV1.count(new LambdaQueryWrapper<>()) == 0;
        index.setInit(init);
        index.setRegister(Boolean.parseBoolean(REGISTER.getValue()));
        return Result.ok(index);
    }

}

@Data
class Index {
    private String startTime;
    /**
     * 网站域名
     */
    private String bindingDomainName;
    /*网站名称*/
    private String name;
    /**
     * 网站名称
     */
    private String shortName;
    /**
     * 版权配置
     */
    private String copyright;
    /**
     * 验证码配置
     */
    private boolean captcha;
    /**
     * 是否初始化
     */
    private boolean init;
    /**
     * 注册用户
     */
    private boolean register;

}