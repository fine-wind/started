package com.example.common;

import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.CaptchaUtils;
import com.example.common.v0.validator.AssertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码接口
 */
@RestController
@Api(tags = "验证码接口")
public class CaptchaController {

    @GetMapping("/captcha.png")
    @ApiOperation(value = "验证码", produces = "application/octet-stream")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", required = true)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        //uuid不能为空
        AssertUtils.isBlank(uuid, UniversalCode.IDENTIFIER_NOT_NULL, "参数不正确");

        //生成验证码
        CaptchaUtils.create(response, uuid);
    }
}
