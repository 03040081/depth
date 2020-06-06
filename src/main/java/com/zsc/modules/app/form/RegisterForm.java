package com.zsc.modules.app.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterForm {

    @NotBlank(message="手机号不能为空")
    private String mobile;

    @NotBlank(message="密码不能为空")
    private String password;
}
