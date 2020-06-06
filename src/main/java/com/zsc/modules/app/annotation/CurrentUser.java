package com.zsc.modules.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当前登录的用户的信息
 * 只要 @CurrentUser userEntity 即可获取到当前用户的信息
 * 通过 HandlerMethodArgumentResolver 的接口在用户登录时获取保存用户信息
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
