package com.example.started.modules.sys.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.started.auth.LoginService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公司的配置
 */
@Log4j2
@RestController
@AllArgsConstructor
@Api(tags = "系统用户")
public class SysUserController {

    final LoginService loginService;

    @RequestMapping(Constant.User.JOIN)
    public void loin(@RequestBody LogInRegisterDTO bo) {
        loginService.join(bo);
        log.debug(bo);
    }
}
