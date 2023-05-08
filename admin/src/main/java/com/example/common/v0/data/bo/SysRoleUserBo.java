package com.example.common.v0.data.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 角色用户关系
 *
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleUserBo extends BaseBo {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户ID
     */
    private String userId;

}
