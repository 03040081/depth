package com.zsc.modules.app.interceptor;

import com.zsc.common.constant.CommonKey;
import com.zsc.common.exception.RException;
import com.zsc.modules.app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }
        //token为空
        if (StringUtils.isBlank(token)) {
            throw new RException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }

        //token过期
        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new RException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(CommonKey.USER_KEY, Long.parseLong(claims.getSubject()));

        return true;
    }
}
