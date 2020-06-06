package com.zsc.modules.oss.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件上传
 */
@Data
public class SysOssEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//URL地址
	private String url;
	//创建时间
	private Date createDate;

}
