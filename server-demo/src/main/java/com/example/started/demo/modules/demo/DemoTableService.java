package com.example.started.demo.modules.demo;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
public interface DemoTableService extends IService<DemoTableEntity> {

    List<DemoTableEntity> ins();
}
