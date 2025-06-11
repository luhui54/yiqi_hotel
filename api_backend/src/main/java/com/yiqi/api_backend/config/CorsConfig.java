package com.yiqi.api_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置 (最终修正版 for Sa-Token)
 * 这个配置是独立于安全框架的，必须被正确设置。
 */
@Configuration
public class CorsConfig {

    // 读取您在 Render 上设置的正确环境变量名 ALLOWED_ORIGINS
    @Value("${ALLOWED_ORIGINS}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 允许发送凭证
        config.setAllowCredentials(true);

        // 设置允许的来源域名 (从环境变量读取)
        if (allowedOrigins != null && !allowedOrigins.trim().isEmpty()) {
            for (String origin : allowedOrigins.split(",")) {
                config.addAllowedOrigin(origin.trim());
            }
        }

        // 允许所有请求头
        config.addAllowedHeader("*");

        // 允许所有请求方法 (GET, POST, PUT, DELETE, OPTIONS, etc.)
        config.addAllowedMethod("*");

        // 将此 CORS 配置应用到所有路径
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}