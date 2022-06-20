package com.example.modules.message.controller;

import com.example.common.annotation.LogOperation;
import com.example.common.constant.Constant;
import com.example.common.data.page.PageData;
import com.example.common.utils.Result;
import com.example.common.validator.ValidatorUtils;
import com.example.common.validator.group.AliyunGroup;
import com.example.common.validator.group.QcloudGroup;
import com.example.common.validator.group.QiniuGroup;
import com.example.modules.message.bo.SysSmsBo;
import com.example.modules.message.dto.SysSmsDTO;
import com.example.modules.message.service.SysSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * 短信服务
 */
@RestController
@RequestMapping("sys/sms")
@Api(tags = "短信服务")
public class SmsController {
    @Autowired
    private SysSmsService sysSmsService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })

    public Result<PageData<SysSmsDTO>> page(@ApiIgnore @RequestParam SysSmsBo params) {
        PageData<SysSmsDTO> page = sysSmsService.selectPage(params);

        return new Result<PageData<SysSmsDTO>>().ok(page);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")

    public Result<?> save(@RequestBody SysSmsDTO dto) {
        //校验数据
        if (dto.getPlatform() == Constant.SmsService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(dto.getConfig(), AliyunGroup.class);
        } else if (dto.getPlatform() == Constant.SmsService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(dto.getConfig(), QcloudGroup.class);
        } else if (dto.getPlatform() == Constant.SmsService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(dto.getConfig(), QiniuGroup.class);
        }

        sysSmsService.save(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result<?> update(@RequestBody SysSmsDTO dto) {
        //校验数据
        if (dto.getPlatform() == Constant.SmsService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(dto.getConfig(), AliyunGroup.class);
        } else if (dto.getPlatform() == Constant.SmsService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(dto.getConfig(), QcloudGroup.class);
        } else if (dto.getPlatform() == Constant.SmsService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(dto.getConfig(), QiniuGroup.class);
        }

        sysSmsService.update(dto);

        return new Result<>();
    }

    @GetMapping("{id}")
    @ApiOperation("信息")

    public Result<SysSmsDTO> info(@PathVariable("id") Long id) {
        SysSmsDTO sms = sysSmsService.getById(id);

        return new Result<SysSmsDTO>().ok(sms);
    }

    @PostMapping("send")
    @ApiOperation("发送短信")
    @LogOperation("发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsCode", value = "短信编码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机好号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "params", value = "参数", paramType = "query", required = true, dataType = "String")
    })

    public Result<?> send(String smsCode, String mobile, LinkedHashMap<String, String> map) {
        sysSmsService.send(smsCode, mobile, map);

        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result<?> delete(@RequestBody Long[] ids) {
        sysSmsService.deleteBatchIds(Arrays.asList(ids));

        return new Result<>();
    }

}
