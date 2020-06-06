package com.zsc.modules.app.biz;

import com.zsc.modules.app.entity.UserEntity;
import com.zsc.modules.app.form.LoginForm;
import com.zsc.modules.app.form.RegisterForm;

import java.util.Map;

/**
 * 用户业务逻辑处理
 */
public interface UserBiz {

    /**
     * 用户登录
     * @param  form
     * @return
     */
    Map<String, Object> login(LoginForm form);

    /**
     * 用户注册
     * @param form
     * @return
     */
    long register(RegisterForm form);
}
