package com.example.started.auth;

import com.example.common.v0.data.dto.LogInRegisterDTO;

import java.util.List;

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

    /**
     * 记录一次尝试登录
     * @param username
     */
    long aLogin(String username);
    long login(String username);
}
