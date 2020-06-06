package com.zsc.modules.app.resolver;

import com.zsc.common.constant.CommonKey;
import com.zsc.modules.app.annotation.CurrentUser;
import com.zsc.modules.app.entity.UserEntity;
import com.zsc.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解析器
 * 获取当前用户的信息
 */
@Component
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter
                .getParameterType()
                .isAssignableFrom(UserEntity.class) && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object obj = nativeWebRequest.getAttribute(CommonKey.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (obj == null) {
            return null;
        }

        UserEntity user = userService.selectById((Long) obj);

        return user;
    }
}
