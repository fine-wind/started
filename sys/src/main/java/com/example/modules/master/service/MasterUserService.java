package com.example.modules.master.service;

import com.example.common.data.service.CrudService;
import com.example.modules.master.bo.MasterUserBo;
import com.example.modules.master.dto.MasterUserDto;
import com.example.modules.master.entity.SysMasterUserEntity;

/**
 * 用户
 */
public interface MasterUserService extends CrudService<MasterUserBo, SysMasterUserEntity, MasterUserDto> {


    /**
     * 判断部门下面是否有用户
     *
     * @param deptId 部门id
     * @return 存在多少人
     */
    boolean isMaster(Long deptId);
}
