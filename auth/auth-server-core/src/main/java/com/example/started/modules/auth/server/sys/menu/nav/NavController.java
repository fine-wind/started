package com.example.started.modules.auth.server.sys.menu.nav;

import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import com.example.started.modules.auth.server.app.AppService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/sys/menu/nav")
@AllArgsConstructor
public class NavController {

    private AppService appService;

    /**
     * 当前登陆人能使用的菜单
     */
    @GetMapping()
    public Result<List<NavVo>> page(@LoginUserId TokenUserId userId) {
        List<NavVo> t = new LinkedList<>();
        t.add(new NavVo("/admin/view/speedometer"));
        return Result.ok(t);
    }
}