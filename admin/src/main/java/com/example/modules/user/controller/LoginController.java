package com.example.modules.user.controller;

import com.example.common.constant.Constant;
import com.example.common.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.data.modules.log.service.SysLogLoginService;
import com.example.common.exception.ServerException;
import com.example.common.exception.UniversalCode;
import com.example.common.utils.*;
import com.example.common.validator.ValidatorUtils;
import com.example.modules.dto.LoginDTO;
import com.example.modules.log.enums.LoginOperationEnum;
import com.example.modules.log.enums.LoginStatusEnum;
import com.example.modules.param.dto.SysParamsDTO;
import com.example.modules.param.service.SysParamsService;
import com.example.modules.security.PasswordConfig;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.enums.UserStatusEnum;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 登录
 */
@RestController
@Api(tags = "登录管理")
public class LoginController {
    @Autowired
    private UserService sysUserService;

    @Autowired
    private SysParamsService paramsService;

    @Autowired
    private SysLogLoginService sysLogLoginService;

    /**
     * 弃用此接口，走 security认证的
     *
     * @param request .
     * @param login   .
     * @return .
     */
    @Deprecated
    @PostMapping(Constant.User.LOGIN)
    @ApiOperation(value = "登录")
    public Result<?> login(HttpServletRequest request, @RequestBody LoginDTO login) {
        //效验数据
        ValidatorUtils.validateEntity(login);

        // todo xing 设置达到登录频率后再启用验证码 弃用此接口
        String value = paramsService.getValue(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA);
        if (Objects.isNull(value)) {
            SysParamsDTO dto = new SysParamsDTO();
            dto.setParamCode(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA);
            value = String.valueOf(Constant.PARAM_CONF.LOGIN_CAPTCHA.LOGIN_CAPTCHA_ERROR_FREQUENCY);
            dto.setParamValue(value);
            dto.setParamType(Constant.PARAM_CONF.CONF_TYPE.SYS);
            dto.setRemark(Constant.PARAM_CONF.LOGIN_CAPTCHA.REMARK);
            // paramsService.save(dto);
        }
        boolean frequency = FrequencyUtil.frequency();
        if (frequency) {
            //验证码是否正确
            boolean flag = CaptchaUtils.validate(login.getUuid(), login.getCaptcha());
            if (!flag) {
                return new Result<>().error(UniversalCode.CAPTCHA_ERROR, "验证码不正确");
            }
        }

        //用户信息
        UserDto user = sysUserService.getByname(login.getUsername());

        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(LoginOperationEnum.LOGIN.value());
        log.setCreateDate(new Date());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));

        //用户不存在
        if (user == null) {
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreatorName(login.getUsername());
            sysLogLoginService.save(log);

            throw new ServerException(UniversalCode.ACCOUNT_PASSWORD_ERROR, "用户不存在");
        }

        //密码错误
        if (!SpringContextUtils.getBean(PasswordConfig.class).matches(login.getPassword(), user.getPassword())) {
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            sysLogLoginService.save(log);

            throw new ServerException(UniversalCode.ACCOUNT_PASSWORD_ERROR, "密码不正确");
        }

        //账号停用
        if (UserStatusEnum.DISABLE.value().equals(user.getStatus())) {
            log.setStatus(LoginStatusEnum.LOCK.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            sysLogLoginService.save(log);

            throw new ServerException(UniversalCode.ACCOUNT_DISABLE, "账号已停用");
        }

        //登录成功
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        sysLogLoginService.save(log);
        return null;
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
