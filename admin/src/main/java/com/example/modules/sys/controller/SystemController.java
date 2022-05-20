package com.example.modules.sys.controller;

import com.example.common.websocket.WebSocketServer;
import com.example.modules.sys.dto.SystemDTO;
import com.sun.management.OperatingSystemMXBean;
import com.example.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 系统接口
 */
@RestController
@Api(tags = "系统接口")
public class SystemController {

    @GetMapping("sys/info")
    @ApiOperation("系统信息")
    public Result<SystemDTO> info() {
        OperatingSystemMXBean osmx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        SystemDTO dto = new SystemDTO();
        dto.setSysName("开始管理系统");
        dto.setSysTime(System.currentTimeMillis());
        dto.setOsName(System.getProperty("os.name"));
        dto.setOsArch(System.getProperty("os.arch"));
        dto.setOsVersion(System.getProperty("os.version"));
        // dto.setUserLanguage(System.getProperty("user.language"));
        dto.setOnline(WebSocketServer.getOnLine());
        dto.setUserDir(System.getProperty("user.dir"));
        dto.setTotalPhysical(osmx.getTotalPhysicalMemorySize() / 1024 / 1024);
        dto.setFreePhysical(osmx.getFreePhysicalMemorySize() / 1024 / 1024);
        dto.setMemoryRate(BigDecimal.valueOf((1 - osmx.getFreePhysicalMemorySize() * 1.0 / osmx.getTotalPhysicalMemorySize()) * 100).setScale(2, RoundingMode.HALF_UP));
        dto.setProcessors(osmx.getAvailableProcessors());
        dto.setJvmName(System.getProperty("java.vm.name"));
        dto.setJavaVersion(System.getProperty("java.version"));
        dto.setJavaHome(System.getProperty("java.home"));
        dto.setJavaTotalMemory(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        dto.setJavaFreeMemory(Runtime.getRuntime().freeMemory() / 1024 / 1024);
        dto.setJavaMaxMemory(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        dto.setUserName(System.getProperty("user.name"));
        dto.setSystemCpuLoad(BigDecimal.valueOf(osmx.getSystemCpuLoad() * 100).setScale(2, RoundingMode.HALF_UP));
        dto.setUserTimezone(System.getProperty("user.timezone"));

        return new Result<SystemDTO>().ok(dto);
    }

}
