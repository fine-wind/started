package com.example.started.demo.modules.demo;

import com.example.started.dynamic.datasource.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("demo_table")
public class DemoTableEntity extends BaseEntity {

    /**
     * 状态  0：失败    1：成功
     */
    private String a;
    /**
     * 用户代理
     */
    private Integer b;
}
