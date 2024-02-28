package com.example.started.auth.modules.sys.bo;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色菜单关系
 *
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleMenuBo extends BaseBo {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色ID
     */
    private List<Long> roleIds;
    /**
     * 菜单ID
     */
    private Long menuId;

}
