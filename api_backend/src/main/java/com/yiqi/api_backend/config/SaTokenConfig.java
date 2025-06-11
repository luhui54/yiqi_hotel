package com.yiqi.api_backend.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import com.yiqi.api_backend.security.StpKit;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限认证配置 (最终修正版)
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Value("${sa-token.jwt-secret-key}")
    private String jwtSecretKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，并为它配置一个“前置函数”
        registry.addInterceptor(new SaInterceptor(handle -> {
            
            // 【【【 核心修改 】】】
            // 在进行登录认证前，先判断是否为 OPTIONS 预检请求，如果是则直接跳过认证
            if (SaHolder.getRequest().isMethod(SaHttpMethod.OPTIONS)) {
                return; // 或者使用 SaRouter.back()，效果相同
            }

            // 如果不是 OPTIONS 请求，则执行原来的登录认证
            StpKit.ADMIN.checkLogin();

        })).addPathPatterns("/sys/**", "/admin/**")
           .excludePathPatterns("/sys/login"); // 确保这里排除了你的真实登录接口路径
    }

    // 您原来的 PostConstruct 配置保持不变，它们是正确的
    @PostConstruct
    public void setSaTokenConfig() {
        SaTokenConfig adminConfig = new SaTokenConfig();
        adminConfig.setTokenName("adminToken");
        adminConfig.setIsReadCookie(false);
        adminConfig.setJwtSecretKey(jwtSecretKey);
        StpKit.ADMIN.setConfig(adminConfig);

        SaTokenConfig userConfig = new SaTokenConfig();
        userConfig.setTokenName("userToken");
        userConfig.setIsReadCookie(false);
        userConfig.setJwtSecretKey(jwtSecretKey);
        StpKit.USER.setConfig(userConfig);
    }

    @PostConstruct
    public void rewriteSaStrategy() {
        SaStrategy.instance.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }
}