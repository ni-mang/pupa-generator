package com.nimang.pupa.common.security;

import cn.dev33.satoken.stp.StpUtil;
import com.nimang.pupa.common.annotation.IgnoreAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移除用户信息 1、防止内存泄漏；2、防止用户乱串
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${sa-token.ignore-path:}")
    private String ignorePath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth ignoreAuth;
        if (handler instanceof HandlerMethod) {
            ignoreAuth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        // 如果有@IgnoreAuth注解，则不验证是否登录
        if (ignoreAuth != null) {
            return true;
        }

        StpUtil.checkLogin();
        return true;
    }

}
