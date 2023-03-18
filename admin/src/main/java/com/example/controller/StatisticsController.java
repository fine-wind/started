package com.example.controller;

import com.example.common.v0.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 *
 *
 * @since 1.0.0 2020-07-06
 */
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计")
public class StatisticsController {

    @PostMapping("{send}")
    @ApiOperation("发送验证码")
    public Result send(@RequestParam("email") String account) {

        return new Result();
    }


}
