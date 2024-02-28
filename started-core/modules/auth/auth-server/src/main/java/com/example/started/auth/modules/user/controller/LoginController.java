package com.example.started.auth.modules.user.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.utils.Result;
import com.example.started.auth.modules.login.v0.service.LoginService;
import com.example.started.auth.client.user.SecurityUser;
import com.example.started.auth.client.user.SecurityUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 */
@RestController
@Api(tags = "账号基本功能接口")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping(Constant.User.JOIN)
    @ApiOperation("注册")
    public Result<?> logInRegister(@Valid @RequestBody LogInRegisterDTO logInRegisterDTO) {
//        if (!CaptchaUtils.validate(logInRegisterDT.getUuid(), logInRegisterDTO.getCaptcha())) {
        // todo throw new ServerException(Constant.UniversalCode.UNAUTHORIZED, "验证码错误");
//        }

        loginService.join(logInRegisterDTO);

        return Result.ok("注册成功");
    }


    /**
     * 保持登录状态接口
     *
     * @param request .
     * @return .
     */
    @PostMapping("maintain")
    @ApiOperation(value = "退出")
    public Result<?> maintain(HttpServletRequest request) {
//
//        //用户信息
//        SysLogLoginEntity log = new SysLogLoginEntity();
//        log.setOperation(LoginOperationEnum.LOGOUT.value());
//        log.setIp(IpUtils.getIpAddr(request));
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        log.setStatus(LoginStatusEnum.SUCCESS.value());
//        log.setCreator(user.getId());
//        log.setCreatorName(user.getUsername());
//        log.setCreateDate(new Date());
//        sysLogLoginService.save(log);

        return new Result<>();
    }

    /**
     * todo xing 弃用此接口
     *
     * @param request .
     * @return .
     */
    @PostMapping("logout")
    @ApiOperation(value = "退出")
    public Result<?> logout(HttpServletRequest request) {

//        //用户信息
//        SysLogLoginEntity log = new SysLogLoginEntity();
//        log.setOperation(LoginOperationEnum.LOGOUT.value());
//        log.setIp(IpUtils.getIpAddr(request));
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        log.setStatus(LoginStatusEnum.SUCCESS.value());
//        log.setCreator(user.getId());
//        log.setCreatorName(user.getUsername());
//        log.setCreateDate(new Date());
//        sysLogLoginService.save(log);

        return new Result<>();
    }

}
