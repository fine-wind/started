package com.example.started.modules.index.page;

import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.Result;
import com.example.started.modules.index.vo.IndexVo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * 首页的接口
 */
@Log4j2
@RestController
@AllArgsConstructor
public class IndexController {

    static long startTime = System.currentTimeMillis();

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<IndexVo> index(@RequestParam(name = "init", defaultValue = "false") boolean init) {
        IndexVo index = new IndexVo();
        index.setStartTime(DateUtil.toString(new Date(startTime)));
        index.setBindingDomainName(THIS_HOST.getValue());
        index.setName(THIS_NAME.getValue());
        index.setShortName(THIS_SHORT_NAME.getValue());
        index.setCopyright(COPYRIGHT.getValue());
        index.setCaptcha(Boolean.parseBoolean(CAPTCHA.getValue()));
        // todo init = init && userServiceV1.count(new LambdaQueryWrapper<>()) == 0;
        index.setInit(init);
        index.setRegister(Boolean.parseBoolean(REGISTER.getValue()));

        return Result.ok(index);
    }

}
