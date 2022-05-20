package com.example.modules.notice.controller;

import com.example.common.annotation.LogOperation;
import com.example.common.constant.Constant;
import com.example.common.data.page.PageData;
import com.example.common.utils.Result;
import com.example.common.validator.AssertUtils;
import com.example.common.validator.ValidatorUtils;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.DefaultGroup;
import com.example.common.validator.group.UpdateGroup;
import com.example.modules.notice.bo.SysNoticeBo;
import com.example.modules.notice.dto.SysNoticeDTO;
import com.example.modules.notice.service.SysNoticeService;
import com.example.modules.notice.service.SysNoticeUserService;
import com.example.modules.security.user.SecurityUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 通知管理
 */
@RestController
@RequestMapping("sys/notice")
@Api(tags = "通知管理")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private SysNoticeUserService sysNoticeUserService;

    @PostMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })

    public Result<PageData<SysNoticeDTO>> page(@RequestBody SysNoticeBo params) {
        PageData<SysNoticeDTO> page = sysNoticeService.selectPage(params);

        return new Result<PageData<SysNoticeDTO>>().ok(page);
    }

    @PostMapping("user/page")
    @ApiOperation("获取被通知的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
    })

    public Result<PageData<SysNoticeDTO>> userPage(@RequestBody SysNoticeBo params) {
        PageData<SysNoticeDTO> page = sysNoticeService.getNoticeUserPage(params);

        return new Result<PageData<SysNoticeDTO>>().ok(page);
    }

    @PostMapping("mynotice/page")
    @ApiOperation("获取我的通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
    })
    public Result<PageData<SysNoticeDTO>> myNoticePage(@ApiIgnore @RequestBody SysNoticeBo params) {
        PageData<SysNoticeDTO> page = sysNoticeService.getMyNoticePage(params);

        return new Result<PageData<SysNoticeDTO>>().ok(page);
    }

    @PutMapping("mynotice/read/{noticeId}")
    @ApiOperation("标记我的通知为已读")
    public Result read(@PathVariable("noticeId") Long noticeId) {
        sysNoticeUserService.updateReadStatus(SecurityUser.getUserId(), noticeId);

        return new Result();
    }

    @GetMapping("mynotice/unread")
    @ApiOperation("我的通知未读读")
    public Result<Long> unRead() {
        Long count = sysNoticeUserService.getUnReadNoticeCount(SecurityUser.getUserId());

        return new Result<Long>().ok(count);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")

    public Result<SysNoticeDTO> get(@PathVariable("id") Long id) {
        SysNoticeDTO data = sysNoticeService.getById(id);

        return new Result<SysNoticeDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")

    public Result<?> save(@RequestBody SysNoticeDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysNoticeService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")

    public Result update(@RequestBody SysNoticeDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysNoticeService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")

    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysNoticeService.deleteByIds(ids);

        return new Result();
    }

}