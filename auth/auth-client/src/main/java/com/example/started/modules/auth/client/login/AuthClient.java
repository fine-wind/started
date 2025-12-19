package com.example.started.modules.auth.client.login;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "auth-service",
        path = "/api/auth"
        // , configuration = AuthClientConfiguration.class todo
)
public interface AuthClient {

    @PostMapping("/login")
    AuthResponse login(@RequestBody LoginRequest request);

    @GetMapping("/validate")
    UserInfo validateToken(@RequestHeader("Authorization") String token);

    @PostMapping("/refresh")
    AuthResponse refreshToken(@RequestBody RefreshRequest request);
}