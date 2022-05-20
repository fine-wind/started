package com.example.modules.sys.service;

import com.example.common.data.service.BaseService;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.bo.SysMenuBo;
import com.example.modules.sys.dto.SysMenuDTO;
import com.example.modules.sys.entity.SysResourcesEntity;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuService extends BaseService<SysMenuBo, SysResourcesEntity> {

    SysMenuDTO get(Long id);

    void save(SysMenuDTO dto);

    void update(SysMenuDTO dto);

    void delete(Long id);

    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<SysMenuDTO> getAllMenuTree(Integer type);

    /**
     * 用户菜单列表
     *
     * @param user 用户
     * @param bo   菜单参数
     */
    List<SysMenuDTO> getMenuList(SecurityUserDetails user, SysMenuBo bo);

    /**
     * 用户菜单树
     *
     * @param user 用户
     * @param type 菜单类型
     */
    List<SysMenuDTO> getUserMenuTree(SecurityUserDetails user, Integer type);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param pid 父菜单ID
     */
    List<SysMenuDTO> getListPid(Long pid);

    /**
     * 根据多个id来获取他们的权限标识
     *
     * @param ids ids
     * @return
     */
    List<String> getListByIds(List<Long> ids);
}
