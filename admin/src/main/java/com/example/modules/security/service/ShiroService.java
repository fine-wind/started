package com.example.modules.security.service;

import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.security.vo.SysMenuVo;

import java.util.List;
import java.util.Map;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Map<String, SysMenuVo> getUserPermissions(SecurityUserDetails user);


    /**
     * 获取用户对应的部门数据权限
     *
     * @param userId 用户ID
     * @return 返回部门ID列表
     */
    List<Long> getDataScopeList(Long userId);
}
