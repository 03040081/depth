package com.zsc.modules.app.controller;

import com.zsc.common.utils.R;
import com.zsc.modules.app.biz.UserBiz;
import com.zsc.modules.app.form.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/app")
public class AppLoginController {

    @Autowired
    private UserBiz userBiz;

    @PostMapping(value = "login")
    public R login(@RequestBody LoginForm form) {
        log.info("[INTO] {}, form:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), form);

        Map<String, Object> map = userBiz.login(form);

        log.info("[OUT] {}, map:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), map);

        return R.ok(map);
    }
}
