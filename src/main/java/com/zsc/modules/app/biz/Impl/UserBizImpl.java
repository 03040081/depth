package com.zsc.modules.app.biz.Impl;

import com.zsc.common.exception.RException;
import com.zsc.common.validator.Assert;
import com.zsc.common.validator.ValidatorUtils;
import com.zsc.modules.app.biz.UserBiz;
import com.zsc.modules.app.entity.UserEntity;
import com.zsc.modules.app.form.LoginForm;
import com.zsc.modules.app.form.RegisterForm;
import com.zsc.modules.app.service.UserService;
import com.zsc.modules.app.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserBizImpl implements UserBiz {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> login(LoginForm form) {
        Map<String, Object> map = new HashMap<>();

        ValidatorUtils.validateEntity(form);
        UserEntity user = userService.queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");
        //密码错误
        if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
            throw new RException("手机号或密码错误");
        }
        String token = jwtUtils.generateToken(user.getUserId());
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return map;
    }

    @Override
    public long register(RegisterForm form) {
        ValidatorUtils.validateEntity(form);
        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());

        long userId = userService.insert(user);

        return userId;
    }
}
