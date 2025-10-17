package com.example.started;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    static int i = 1;

    @GetMapping(value = "/index")
    public Object showImage() {
        return i++;
    }

}
