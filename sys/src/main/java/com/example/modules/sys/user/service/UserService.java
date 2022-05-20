package com.example.modules.sys.user.service;

import com.example.common.data.dto.LogInRegisterDTO;
import com.example.common.data.service.CrudService;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.user.bo.UserBo;
import com.example.modules.sys.user.dto.UserDto;
import com.example.modules.sys.user.entity.SysUserEntity;

import java.util.List;

/**
 * 用户
 */
public interface UserService extends CrudService<UserBo, SysUserEntity, UserDto> {
    /**
     * 登录或注册
     *
     * @param logInRegisterDTO 参数
     */
    void join(LogInRegisterDTO logInRegisterDTO);

    UserDto getByname(String mobile);

    UserDto getUserByUserId(Long userId);

    /**
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);

    void updateUserInfo(UserDto userDTO);

    SecurityUserDetails getUserInfoVo(String token);

    List<Long> getUserIdListByDeptId(List<Long> deptIdList);

    /**
     * 判断部门下面是否有用户
     *
     * @param deptId 部门id
     * @return 存在多少人
     */
    Long getCountByDeptId(Long deptId);
}
