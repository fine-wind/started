package com.example.started.demo.modules.freemarker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.ui.Model;

@Controller
@RequestMapping()
@AllArgsConstructor
public class FreemarkerController {

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("demos", 11);
        return "index";
    }

}
