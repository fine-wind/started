package com.example.started.modules.sys.service;

import com.example.started.modules.sys.bo.SysMenuBo;
import com.example.started.modules.sys.dto.SysMenuDTO;
import com.example.common.v0.data.service.BaseService;
import com.example.started.modules.sys.entity.SysResourcesEntity;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
public interface SysMenuService extends BaseService<SysMenuBo, SysResourcesEntity>, CommandLineRunner {

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
     * @param bo 菜单参数
     */
    List<SysMenuDTO> getMenuList(SysMenuBo bo);

    /**
     * 用户菜单树
     *
     * @param type 菜单类型
     */
    List<SysMenuDTO> getUserMenuTree(Integer type);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param pid 父菜单ID
     */
    List<SysMenuDTO> getListPid(Long pid);

    /**
     * 根据多个id来获取他们的权限标识集合
     *
     * @param ids ids
     * @return 权限标识集合
     */
    Set<String> getListByIds(List<Long> ids);
}
