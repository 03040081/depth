package com.zsc.modules.sys.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统验证码
 */
@Data
public class SysCaptchaEntity {
    private String uuid;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;

}
