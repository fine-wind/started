package com.example.modules.sys.login.v0.service;

import com.example.common.v0.data.dto.LogInRegisterDTO;

import java.util.List;

/**
 * 用户
 */
public interface LoginService {

    void join(LogInRegisterDTO mobile);

    List<String> getUserIdListByDeptId(List<Long> deptIdList);

    /**
     * 判断部门下面是否有用户
     *
     * @param deptId 部门id
     * @return 存在多少人
     */
    Long getCountByDeptId(Long deptId);
}
