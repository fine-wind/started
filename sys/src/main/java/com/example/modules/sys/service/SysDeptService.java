package com.example.modules.sys.service;

import com.example.common.v0.data.bo.SysDeptBo;
import com.example.common.v0.data.entity.SysGroupEntity;
import com.example.common.v0.data.service.BaseService;
import com.example.modules.sys.dept.dto.SysDeptDTO;

import java.util.List;

/**
 * 部门管理
 */
public interface SysDeptService extends BaseService<SysDeptBo, SysGroupEntity> {

    List<SysDeptDTO> list(SysDeptBo params);

    SysDeptDTO get(Long id);

    void save(SysDeptDTO dto);

    void update(SysDeptDTO dto);

    void delete(Long id);

    /**
     * 根据部门ID，获取本部门及子部门ID列表
     *
     * @param id 部门ID
     */
    List<Long> getSubDeptIdList(Long id);
}
