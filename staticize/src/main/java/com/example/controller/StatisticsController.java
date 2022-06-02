package com.example.controller;

import com.example.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码
 *
 *
 * @since 1.0.0 2020-07-06
 */
@RestController
@RequestMapping("api/statistics")
@Api(tags = "统计")
public class StatisticsController {

    @PostMapping("{send}")
    @ApiOperation("发送验证码")
    public Result send(@RequestParam("email") String account) {

        return new Result();
    }


}
