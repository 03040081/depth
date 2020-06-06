package com.zsc.modules.app.config;

import com.zsc.modules.app.interceptor.AuthorizationInterceptor;
import com.zsc.modules.app.resolver.CurrentUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;

    /**
     * 增加token认证的拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/app/**")
                .excludePathPatterns("/app/login", "/app/register");
    }

    /**
     * 通过该解析器，在用户登录时，获取到用户的信息。实现了 @CurrentUser 注解
     * @see com.zsc.modules.app.annotation.CurrentUser
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserHandlerMethodArgumentResolver);
    }
}
