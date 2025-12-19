package com.example.started.modules.auth.server.app;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api/app")
@AllArgsConstructor
public class AppController {

    private AppService appService;

    /**
     * todo
     */
    public void page() {

    }
}