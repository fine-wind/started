package com.example.started.modules.sys.service;

import com.example.started.modules.sys.dto.SysRoleMenuDto;
import com.example.common.v0.data.service.CrudService;
import com.example.started.modules.sys.bo.SysRoleMenuBo;
import com.example.started.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
public interface SysRoleMenuService extends CrudService<SysRoleMenuBo, SysRoleMenuEntity, SysRoleMenuDto> {

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     */
    List<Long> getMenuIdList(Long roleId);
    List<Long> getMenuIdLists(List<Long> roleId);

    /**
     * 保存或修改
     *
     * @param roleId     角色ID
     * @param menuIdList 菜单ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色id，删除角色菜单关系
     *
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据菜单id，删除角色菜单关系
     *
     * @param menuId 菜单id
     */
    void deleteByMenuId(Long menuId);
}
