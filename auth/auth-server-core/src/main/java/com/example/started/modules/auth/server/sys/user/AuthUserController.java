package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import com.example.started.modules.auth.validate.dto.UserInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @PostMapping("/all")
    public Result<List<AuthUserAllVo>> all(@LoginUserId TokenUserId tokenUserId, @RequestBody AuthUserAllBo bo) {
        return Result.ok(authUserService.all(tokenUserId.getUserId(), bo));
    }

    /**
     * 用户总量
     *
     * @param tokenUserId .
     * @param bo          .
     * @return .
     */
    @PostMapping("/count")
    public Result<Long> count(@LoginUserId TokenUserId tokenUserId, @RequestBody AuthUserAllBo bo) {
        return Result.ok(authUserService.count(tokenUserId.getUserId(), bo));
    }

    /**
     * 用户详情
     *
     * @param tokenUserId .
     * @return .
     */
    @GetMapping("/info")
    public Result<UserInfoVo> info(@LoginUserId TokenUserId tokenUserId) {

        AuthUserEntity byUsername = authUserService.getByUsername(tokenUserId.getUserId());

        return Result.ok(AuthUserEntity.toVo(byUsername));
    }

    @PostMapping("/add")
    public Result<UserInfoVo> add(@LoginUserId TokenUserId tokenUserId, @RequestBody AuthUserAddBo bo) {


        authUserService.register(tokenUserId, bo);

        return Result.ok();
    }

    @DeleteMapping("/all")
    public Result<?> del(@LoginUserId TokenUserId tokenUserId, @RequestParam("ids") String ids) {
        return Result.error("功能开发中");
    }

}
