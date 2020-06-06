package com.zsc.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 */
@Data
public class SysUserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

	
}
