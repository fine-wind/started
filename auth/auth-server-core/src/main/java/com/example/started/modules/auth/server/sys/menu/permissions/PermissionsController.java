package com.example.started.modules.auth.server.sys.menu.permissions;

import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.sys.user.AuthUserService;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sys/menu/permissions")
@AllArgsConstructor
public class PermissionsController {

    private final AuthUserService authUserService;

    @GetMapping()
    public Result<?> index(@LoginUserId TokenUserId tokenUserId) {

        return Result.ok();
    }

}
