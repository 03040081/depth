package com.zsc.modules.app.service;

import com.zsc.modules.app.entity.UserEntity;
import com.zsc.modules.app.form.LoginForm;
import com.zsc.modules.app.form.RegisterForm;

public interface UserService {

    /**
     * 通过用户id获取用户信息
     * @param userId
     * @return
     */
    UserEntity selectById(Long userId);

    /**
     * 通过用户的手机号码查询用户的信息
     * @param mobile
     * @return
     */
    UserEntity queryByMobile(String mobile);
    /**
     * 用户注册
     * @param user
     * @return
     */
    long insert(UserEntity user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int update(UserEntity user);
}
