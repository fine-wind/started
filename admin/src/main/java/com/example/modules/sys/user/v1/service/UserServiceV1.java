package com.example.modules.sys.user.v1.service;

import com.example.common.v1.base.service.BaseServiceV1;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.entity.SysUserEntity;

import java.util.List;

/**
 * 用户
 */
public interface UserServiceV1 extends BaseServiceV1<UserDto, SysUserEntity> {


    UserDto getByName(String mobile);

    UserDto getUserByUserId(String userId);

    boolean updatePassword(String userId, String oldPassword, String newPassword);

    void updateUserInfo(UserDto userDTO);

    SecurityUserDetails getUserInfoVo(String token);

    List<String> getUserIdListByDeptId(List<Long> deptIdList);

    /**
     * 判断部门下面是否有用户
     *
     * @param deptId 部门id
     * @return 存在多少人
     */
    Long getCountByDeptId(Long deptId);
}
