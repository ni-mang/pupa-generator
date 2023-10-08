package com.nimang.pupa.common.config;

import com.nimang.pupa.common.security.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token登录认证
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${sa-token.ignore-path:}")
    private String ignorePath;

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(ignorePath.split(","));
    }
}
