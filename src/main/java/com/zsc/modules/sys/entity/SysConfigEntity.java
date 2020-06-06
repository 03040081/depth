package com.zsc.modules.sys.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 */
@Data
public class SysConfigEntity {
	private Long id;
	@NotBlank(message="参数名不能为空")
	private String paramKey;
	@NotBlank(message="参数值不能为空")
	private String paramValue;
	private String remark;

}
