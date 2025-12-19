package com.example.started.modules.auth.server.sys.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.started.common.v0.utils.StringUtil;
import com.example.started.dynamic.datasource.BaseEntity;
import com.example.started.modules.auth.validate.dto.UserInfoVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 *
 * @since 1.0.0
 */
@Data
@TableName("auth_user")
@EqualsAndHashCode(callSuper = true)
public class AuthUserEntity extends BaseEntity {

    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 用户状态
     * 0：未激活
     * 100：正常使用
     */
    private Integer status;

    public static UserInfoVo toVo(AuthUserEntity entity) {
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserName(entity.getUsername());
        userInfoVo.setShowName(StringUtil.defaultValue(entity.getShowName(), entity.getUsername()));
        return userInfoVo;
    }
}
