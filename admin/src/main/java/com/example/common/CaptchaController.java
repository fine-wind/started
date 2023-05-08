package com.example.common;

import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.CaptchaUtils;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.AssertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * 验证码接口
 */
@RestController
@Api(tags = "验证码接口")
public class CaptchaController {

    @GetMapping("/captcha.png")
    @ApiOperation(value = "验证码", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", required = true)
    public Result<?> captcha(@RequestParam(defaultValue = "100") Integer w, @RequestParam(defaultValue = "35") Integer h) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String img = CaptchaUtils.create(w, h, uuid);
        HashMap<Object, Object> data = new HashMap<>(2);
        data.put("uuid", uuid);
        data.put("img", img);
        return Result.ok(data);
    }
}
