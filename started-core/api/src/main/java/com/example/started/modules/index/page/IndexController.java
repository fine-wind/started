package com.example.started.modules.index.page;

import com.example.common.v0.utils.CaptchaUtils;
import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.Result;
import com.example.started.modules.index.vo.IndexVo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * 首页的接口
 */
@Log4j2
@RestController
@AllArgsConstructor
public class IndexController {

    static long startTime = System.currentTimeMillis();

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public IndexVo index(@RequestParam(name = "init", defaultValue = "false") boolean init) {
        IndexVo index = new IndexVo();
        index.setStartTime(DateUtil.toString(new Date(startTime)));
        index.setDomainName(THIS_HOST.getValue());
        index.setName(THIS_NAME.getValue());
        index.setShortName(THIS_SHORT_NAME.getValue());
        index.setCopyright(COPYRIGHT.getValue());
        index.setCaptcha(Boolean.parseBoolean(CAPTCHA.getValue()));
        // init = init && userServiceV1.count(new LambdaQueryWrapper<>()) == 0;
        index.setInit(init);
        index.setRegister(Boolean.parseBoolean(REGISTER.getValue()));
        return index;
    }

    @GetMapping(value = "/captcha.png", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> captcha() throws IOException {
        String uuid = UUID.randomUUID().toString();
        Map<String, String> map = new HashMap<>(2);
        map.put("uuid", uuid);
        map.put("img", CaptchaUtils.create(uuid));
        return Result.ok(map);
    }

}
