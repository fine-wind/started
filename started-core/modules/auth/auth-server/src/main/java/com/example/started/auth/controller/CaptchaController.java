package com.example.started.auth.controller;

import com.example.common.v0.utils.CaptchaUtils;
import com.example.common.v0.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * 验证码接口
 */
@RestController
@Api(tags = "验证码接口")
@AllArgsConstructor
public class CaptchaController {

    @GetMapping("/captcha.png")
    @ApiOperation(value = "验证码", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", required = true)
    public Result<?> captcha(
            @RequestParam(defaultValue = "100") Integer w,
            @RequestParam(defaultValue = "35") Integer h,
            @RequestParam(required = false) String uuid
    ) throws IOException {

        /* 删除旧的验证码*/
        if (Objects.nonNull(uuid)) {
            // redisUtils.delCache(CacheCommonKeys.getCaptchaKey(uuid));
        }

        String work = CaptchaUtils.work();
        uuid = UUID.randomUUID().toString();
        // redisUtils.setCache(CacheCommonKeys.getCaptchaKey(uuid), work, 300L);

        HashMap<Object, Object> data = new HashMap<>(2);
        data.put("uuid", uuid);
        data.put("img", CaptchaUtils.create(w, h, work));
        return Result.ok(data);
    }
}
