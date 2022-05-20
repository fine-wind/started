package com.example.modules.sys.bo;

import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色数据权限
 *
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleDataScopeBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色ID
     */
    private List<Long> roleIds;
    /**
     * 部门ID
     */
    private Long deptId;

}
