package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import com.example.started.modules.auth.validate.dto.UserInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/user/info")
@AllArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @GetMapping()
    public Result<UserInfoVo> index(@LoginUserId TokenUserId tokenUserId) {

        AuthUserEntity byUsername = authUserService.getByUsername(tokenUserId.getUserId());

        return Result.ok(AuthUserEntity.toVo(byUsername));
    }

}
