package com.example.started.auth.modules.role;

import com.example.common.v0.data.bo.SysRoleUserBo;
import com.example.started.auth.modules.role.entity.SysRoleUserEntity;
import com.example.common.v0.data.service.BaseService;

import java.util.List;

/**
 * 角色用户关系
 *
 * @since 1.0.0
 */
public interface SysRoleUserService extends BaseService<SysRoleUserBo, SysRoleUserEntity> {

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID列表
     */
    void saveOrUpdate(String userId, List<Long> roleIdList);

    /**
     * 根据角色ids，删除角色用户关系
     *
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据用户id，删除角色用户关系
     *
     * @param userIds 用户ids
     */
    void deleteByUserId(String userIds);

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     */
    List<Long> getRoleIdList(String userId);
}
