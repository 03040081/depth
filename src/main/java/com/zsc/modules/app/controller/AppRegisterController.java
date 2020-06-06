package com.zsc.modules.app.controller;

import com.zsc.common.utils.R;
import com.zsc.modules.app.biz.UserBiz;
import com.zsc.modules.app.form.RegisterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/app")
public class AppRegisterController {

    @Autowired
    private UserBiz userBiz;

    @PostMapping(value = "register")
    public R register(@RequestBody RegisterForm form) {

        log.info("[INTO] {}, form:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), form);

        long userId = userBiz.register(form);

        log.info("[OUT] {}, userId:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), userId);

        return R.ok();
    }
}
