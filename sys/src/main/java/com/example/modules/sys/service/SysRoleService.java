package com.example.modules.sys.service;

import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.modules.sys.bo.SysRoleBo;
import com.example.modules.sys.dto.SysRoleDTO;
import com.example.modules.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色
 */
public interface SysRoleService extends BaseService<SysRoleBo, SysRoleEntity> {

    PageData<SysRoleDTO> page(SysRoleBo params);

    List<SysRoleEntity> getList(SysRoleBo params);

    SysRoleDTO get(Long id);

    void save(SysRoleDTO dto);

    void update(SysRoleDTO dto);

    void delete(List<Long> ids);

}
