package com.example.modules.security.dto;

import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户Token
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserTokenDto extends BaseBo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户token
	 */
	private String token;
	/**
	 * 过期时间
	 */
	private Date expireDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

}
