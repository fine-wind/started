package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.v0.utils.StringUtil;
import com.example.started.modules.auth.validate.dto.UserInfoVo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

/**
 * 用户实体
 *
 * @since 1.0.0
 */
@Entity
@Data
@Table(name = "auth_user")
public class AuthUserEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
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


    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private Long updater;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除标志
     */
    private Integer delFlag;
}
