package com.example.started.auth.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 系统数据
 *
 * @since 1.0.0
 */
@Data
@ApiModel(value = "系统数据")
public class SystemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 系统名称
     */
    private String sysName;
    private Long sysTime;
    private String osName;
    private String osArch;
    private String osVersion;
    /**
     * 在线人数
     */
    private int online;
    private String userDir;
    private Long totalPhysical;
    private Long freePhysical;
    private BigDecimal memoryRate;
    private Integer processors;
    private String jvmName;
    private String javaVersion;
    private String javaHome;
    private Long javaTotalMemory;
    private Long javaFreeMemory;
    private Long javaMaxMemory;
    private String userName;
    private BigDecimal systemCpuLoad;
    private String userTimezone;

}
