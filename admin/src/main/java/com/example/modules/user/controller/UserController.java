package com.example.modules.user.controller;

import com.example.common.data.dto.LogInRegisterDTO;
import com.example.common.security.JwtUtils;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.entity.SysUserEntity;
import com.example.modules.sys.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.common.data.annotation.Login;
import com.example.common.data.annotation.LoginUser;
import com.example.common.constant.Constant;
import com.example.common.exception.ServerException;
import com.example.common.utils.Result;
import com.example.common.validator.ValidatorUtils;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.UpdateGroup;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 用户相关接口
 *
 * @since 1.0.0 2020-07-06
 */
@RestController
@Api(tags = "用户相关接口")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Constant.User.JOIN)
    @ApiOperation("注册")
    // @PreAuthorize("permitAll()")
    public Result<?> logInRegister(@RequestBody LogInRegisterDTO logInRegisterDTO, HttpServletResponse response) {
        ValidatorUtils.validateEntity(logInRegisterDTO, AddGroup.class);
//        if (!CaptchaUtils.validate(logInRegisterDT.getUuid(), logInRegisterDTO.getCaptcha())) {
        // todo throw new ServerException(Constant.UniversalCode.UNAUTHORIZED, "验证码错误");
//        }

        userService.join(logInRegisterDTO);

        return new Result<>().ok("");
    }

    @PostMapping("/api/user/userInfo")
    @ApiOperation("根据token 获取用户信息")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result<SecurityUserDetails> userInfo(@RequestHeader(name = Constant.REQUEST.HEADER.TOKEN, required = false) String token) {

        SecurityUserDetails userInfoDTO;
        if (Objects.nonNull(token) && token.length() > 9) {
            Claims decoder = JwtUtils.decoder(token);
            assert decoder != null;
            String username = decoder.getSubject();
            userInfoDTO = userService.getUserInfoVo(username);
        } else {
            userInfoDTO = SecurityUser.getUser(true);
        }
        if (userInfoDTO == null) {
            throw new ServerException(Constant.UniversalCode.UNAUTHORIZED, "无此用户");
        }
        return new Result<SecurityUserDetails>().ok(userInfoDTO);
    }

    @Login
    @PostMapping("/api/user/updateUserInfo")
    @ApiOperation("更新用户信息和头像")
    public Result<?> updateUserInfo(@ApiIgnore @LoginUser SysUserEntity user, @RequestBody UserDto userDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userDTO, UpdateGroup.class);
        userDTO.setId(user.getId());
        userService.updateUserInfo(userDTO);
        return new Result<>();
    }

    /**
     * todo xing 重置密码
     *
     * @param email
     * @param confirmpassword
     * @param newpassword
     * @param code
     * @return
     */
    @PostMapping("/api/user/restPwd")
    @ApiOperation("重置密码")
    public Result<?> restPwd(@RequestParam(value = "email") String email,
                             @RequestParam("confirmpassword") String confirmpassword,
                             @RequestParam("newpassword") String newpassword,
                             @RequestParam("code") String code
    ) {
        //两次输入的密码不一致
//        if (!confirmpassword.equals(newpassword)) {
//            return new Result<>().error("两次输入的密码不一致！");
//        }
//        UserVo user = userService.getByname(email);
//        if (user == null) {
//            return new Result<>().error("邮箱不存在！");
//        }
//        userService.restPwd(user.getId(), newpassword, email, code);
        return new Result<>();
    }

}
