package com.example.admin.logcontroller;

import com.example.common.constant.Constant;
import com.example.common.data.modules.log.bo.SysLogLoginBo;
import com.example.common.data.modules.log.dto.SysLogLoginDTO;
import com.example.common.data.modules.log.service.SysLogLoginService;
import com.example.common.data.page.PageData;
import com.example.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 登录日志
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/login")
@Api(tags = "登录日志")
public class SysLogLoginController {
    @Autowired
    private SysLogLoginService sysLogLoginService;

    @PostMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.PAGE.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态  0：失败    1：成功    2：账号已锁定", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorName", value = "用户名", paramType = "query", dataType = "String")
    })

    public Result<PageData<SysLogLoginDTO>> page(@ApiIgnore @RequestBody SysLogLoginBo params) {
        PageData<SysLogLoginDTO> page = sysLogLoginService.page(params);

        return new Result<PageData<SysLogLoginDTO>>().ok(page);
    }

}
