package com.example.modules.user.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.dto.LogInRegisterDTO;
import com.example.common.v0.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.v0.data.modules.log.service.SysLogLoginService;
import com.example.common.v0.utils.*;
import com.example.modules.log.enums.LoginOperationEnum;
import com.example.modules.log.enums.LoginStatusEnum;
import com.example.modules.param.service.SysParamsService;
import com.example.started.verify.security.user.SecurityUser;
import com.example.started.verify.security.user.SecurityUserDetails;
import com.example.modules.sys.login.v0.service.LoginService;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 登录
 */
@RestController
@Api(tags = "登录管理")
public class LoginController {
    @Autowired
    private UserServiceV1 sysUserService;

    @Autowired
    private SysParamsService paramsService;

    @Autowired
    private SysLogLoginService sysLogLoginService;
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

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
     * 获取我的信息
     *
     * @return .
     */
    @GetMapping("/getMeInfo")
    @ApiOperation(value = "登录")
    public Result<SecurityUserDetails> getMeInfo() {
        SecurityUserDetails user = SecurityUser.getUser();
        return Result.ok(user);
    }
//
//    /**
//     * 走 security认证的
//     *
//     * @param request .
//     * @param login   .
//     * @return .
//     */
//    @Deprecated
//    @PostMapping(Constant.User.LOGIN)
//    @ApiOperation(value = "登录")
//    public Result<?> login(HttpServletRequest request, @RequestBody LoginDTO login) {
//        //效验数据
//        ValidatorUtils.validateEntity(login);
//
//        // todo xing 设置达到登录频率后再启用验证码 弃用此接口
//        String value = paramsService.getValue(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA);
//        if (Objects.isNull(value)) {
//            SysParamsDTO dto = new SysParamsDTO();
//            dto.setParamCode(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA);
//            value = String.valueOf(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA_ERROR_FREQUENCY);
//            dto.setParamValue(value);
//            dto.setParamType(Constant.PARAM_CONF.CONF_TYPE.SYS);
//            dto.setRemark(Constant.PARAM_CONF.LOGIN_CAPTCHA.REMARK);
//            // paramsService.save(dto);
//        }
//        boolean frequency = FrequencyUtil.frequency();
//        if (frequency) {
//            //验证码是否正确
//            if (!CaptchaUtils.validate(login.getUuid(), login.getCaptcha())) {
//                return Result.error(UniversalCode.CAPTCHA_ERROR, "验证码过期或不正确");
//            }
//        }
//
//        //用户信息
//        UserDto user = sysUserService.getByName(login.getUsername());
//
//        SysLogLoginEntity log = new SysLogLoginEntity();
//        log.setOperation(LoginOperationEnum.LOGIN.value());
//        log.setCreateDate(new Date());
//        log.setIp(IpUtils.getIpAddr(request));
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        log.setIp(IpUtils.getIpAddr(request));
//
//        //用户不存在
//        if (user == null) {
//            log.setStatus(LoginStatusEnum.FAIL.value());
//            log.setCreatorName(login.getUsername());
//            sysLogLoginService.save(log);
//
//            throw new ServerException(UniversalCode.ACCOUNT_PASSWORD_ERROR, "用户不存在");
//        }
//
//        //密码错误
//        // if (!SpringContextUtils.getBean(PasswordConfig.class).matches(login.getPassword(), user.getPassword())) {
//        //     log.setStatus(LoginStatusEnum.FAIL.value());
//        //     log.setCreator(user.getId());
//        //     log.setCreatorName(user.getUsername());
//        //     sysLogLoginService.save(log);
//        //     throw new ServerException(UniversalCode.ACCOUNT_PASSWORD_ERROR, "密码不正确");
//        // }
//
//        //账号停用
//        if (UserStatusEnum.DISABLE.value().equals(user.getStatus())) {
//            log.setStatus(LoginStatusEnum.LOCK.value());
//            log.setCreator(user.getId());
//            log.setCreatorName(user.getUsername());
//            sysLogLoginService.save(log);
//
//            throw new ServerException(UniversalCode.ACCOUNT_DISABLE, "账号已停用");
//        }
//
//        //登录成功
//        log.setStatus(LoginStatusEnum.SUCCESS.value());
//        log.setCreator(user.getId());
//        log.setCreatorName(user.getUsername());
//        sysLogLoginService.save(log);
//        return null;
//    }

    /**
     * todo xing 弃用此接口
     *
     * @param request .
     * @return .
     */
    @PostMapping("logout")
    @ApiOperation(value = "退出")
    public Result<?> logout(HttpServletRequest request) {
        SecurityUserDetails user = SecurityUser.getUser();

        //用户信息
        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(LoginOperationEnum.LOGOUT.value());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        log.setCreateDate(new Date());
        sysLogLoginService.save(log);

        return new Result<>();
    }

}
