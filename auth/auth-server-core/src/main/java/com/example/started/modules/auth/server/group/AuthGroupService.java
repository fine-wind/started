package com.example.started.modules.auth.server.group;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限分组
 *
 * @since 1.0.0
 */
public interface AuthGroupService extends IService<AuthGroupEntity> {

    List<AuthGroupEntity> ins();
}
