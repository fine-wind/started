package com.example.started.auth.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色数据权限
 *
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysRoleDataScopeDto {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 部门ID
	 */
	private Long deptId;

}
