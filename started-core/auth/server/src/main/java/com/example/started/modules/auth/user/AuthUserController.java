package com.example.started.modules.auth.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @GetMapping(value = "/index")
    public Object index() {
        List<AuthUserEntity> ins = authUserService.ins();

        authUserService.updateBatchById(ins);
        Set<Long> collect = ins.stream().map(AuthUserEntity::getId).collect(Collectors.toSet());
        authUserService.removeBatchByIds(collect);
        return "SUCCESS";
    }

}
