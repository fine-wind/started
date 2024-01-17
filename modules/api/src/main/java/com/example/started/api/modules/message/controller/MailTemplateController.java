package com.example.started.api.modules.message.controller;

import com.example.started.api.modules.message.bo.SysMailTemplateBo;
import com.example.started.api.modules.message.dto.SysMailTemplateDTO;
import com.example.started.api.modules.message.email.EmailConfig;
import com.example.started.api.modules.param.service.SysParamsService;
import com.example.started.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.Result;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.example.started.api.modules.message.service.SysMailTemplateService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Map;


/**
 * 邮件模板
 */
@RestController
@RequestMapping("sys/mailtemplate")
@Api(tags = "邮件模板")
public class MailTemplateController {
    @Autowired
    private SysMailTemplateService sysMailTemplateService;
    @Autowired
    private SysParamsService sysParamsService;

    private final static String KEY = Constant.MAIL_CONFIG_KEY;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "name", paramType = "query", dataType = "String")
    })
    public Result<PageData<SysMailTemplateDTO>> page(@ApiIgnore @RequestParam SysMailTemplateBo params) {
        PageData<SysMailTemplateDTO> page = sysMailTemplateService.selectPage(params);

        return new Result<PageData<SysMailTemplateDTO>>().ok(page);
    }

    @GetMapping("/config")
    @ApiOperation("获取配置信息")
    public Result<EmailConfig> config() {
        EmailConfig config = sysParamsService.getValueObject(KEY, EmailConfig.class);

        return new Result<EmailConfig>().ok(config);
    }

    @PostMapping("/saveConfig")
    @ApiOperation("保存配置信息")
    @LogOperation("保存配置信息")
    public Result<?> saveConfig(@RequestBody EmailConfig config) {
        //校验数据
        ValidatorUtils.validateEntity(config);

        sysParamsService.updateValueByCode(KEY, new Gson().toJson(config));

        return new Result<>();
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<SysMailTemplateDTO> info(@PathVariable("id") Long id) {
        SysMailTemplateDTO sysMailTemplate = sysMailTemplateService.getById(id);

        return new Result<SysMailTemplateDTO>().ok(sysMailTemplate);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result<?> save(SysMailTemplateDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysMailTemplateService.save(dto);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    public Result<?> update(SysMailTemplateDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysMailTemplateService.update(dto);

        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    public Result<?> delete(@RequestBody Long[] ids) {
        sysMailTemplateService.deleteBatchIds(Arrays.asList(ids));

        return new Result<>();
    }

    @PostMapping("/send")
    @ApiOperation("发送邮件")
    @LogOperation("发送邮件")
    public Result<?> send(Long id, String mailTo, String mailCc, Map<String, Object> map) throws Exception {
        boolean flag = sysMailTemplateService.sendMail(id, mailTo, mailCc, map);
        if (flag) {
            return new Result<>();
        }

        return new Result<>().error("邮件发送失败");
    }

}
