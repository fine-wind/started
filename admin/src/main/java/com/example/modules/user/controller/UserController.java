package com.example.modules.user.controller;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.annotation.Login;
import com.example.common.v0.data.annotation.LoginUser;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.security.JwtUtils;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.UpdateGroup;
import com.example.started.verify.security.user.SecurityUser;
import com.example.started.verify.security.user.SecurityUserDetails;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.entity.SysUserEntity;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;

/**
 * 用户相关接口
 *
 * @since 1.0.0 2020-07-06
 */
@RestController
@Api(tags = "用户相关接口")
public class UserController {

    private final UserServiceV1 userService;


    @Autowired
    public UserController(UserServiceV1 userService) {
        this.userService = userService;
    }


    @PostMapping("/user/userInfo")
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
            userInfoDTO = SecurityUser.getUser();
        }
        if (userInfoDTO == null) {
            throw new ServerException(Constant.UniversalCode.UNAUTHORIZED, "无此用户");
        }
        return new Result<SecurityUserDetails>().ok(userInfoDTO);
    }

    @Login
    @PostMapping("/user/updateUserInfo")
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
     * 重置密码时 该账号所有登录状态需失效
     *
     * @param email           .
     * @param confirmpassword .
     * @param newpassword     .
     * @param code            .
     * @return .
     */
    @PostMapping("/user/restPwd")
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
