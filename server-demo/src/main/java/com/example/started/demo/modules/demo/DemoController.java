package com.example.started.demo.modules.demo;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class DemoController {

    private final DemoTableService demoTableService;

    @PostConstruct
    @GetMapping(value = "/index")
    public Object index() {
        List<DemoTableEntity> ins = demoTableService.ins();
        for (DemoTableEntity in : ins) {
            in.setB(22);
        }
        demoTableService.updateBatchById(ins);
        Set<Long> collect = ins.stream().map(DemoTableEntity::getId).collect(Collectors.toSet());
        demoTableService.removeBatchByIds(collect);
        return "SUCCESS";
    }

}
