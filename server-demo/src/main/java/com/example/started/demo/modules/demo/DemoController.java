package com.example.started.demo.modules.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class DemoController {

    private final DemoTableService demoTableService;

    @GetMapping(value = "/index")
    public Object index() {
        demoTableService.test();
        return "SUCCESS";
    }

}
