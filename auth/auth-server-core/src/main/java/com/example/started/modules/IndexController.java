package com.example.started.modules;

import com.example.started.common.v0.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Log4j2
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(value = "/")
    public Result<?> index() {
        HashMap<String, Object> conf = new HashMap<>();
        conf.put("name", "公司好名单");
        return Result.ok(conf);
    }

}
