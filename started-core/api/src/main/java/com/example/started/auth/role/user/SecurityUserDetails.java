package com.example.started.auth.role.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 登录用户信息
 */
@Data
public class SecurityUserDetails implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 草鸡管理员
     *
     * @see Constant.Status
     */
    private Integer superAdmin;
    /**
     * 部门数据权限
     */
    private List<Long> deptIdList;

    private Collection<? extends GrantedAuthority> authorities;

    public String getUsername() {
        return Objects.isNull(username) ? "" : username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return 账号是否已经过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
