package com.example.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信日志
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_sms_log")
public class SysSmsLogEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 短信编码
	 */
	private String smsCode;
	/**
	 * 平台类型
	 */
	private Integer platform;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 参数1
	 */
	@TableField("params_1")
	private String params1;
	/**
	 * 参数2
	 */
	@TableField("params_2")
	private String params2;
	/**
	 * 参数3
	 */
	@TableField("params_3")
	private String params3;
	/**
	 * 参数4
	 */
	@TableField("params_4")
	private String params4;
	/**
	 * 发送状态  0：失败   1：成功
	 */
	private Integer status;

}
