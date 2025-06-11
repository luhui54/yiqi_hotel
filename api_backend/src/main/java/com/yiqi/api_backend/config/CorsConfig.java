package com.yiqi.api_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${spring.web.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许指定来源的跨域请求
        if (allowedOrigins.contains(",")) {
            String[] origins = allowedOrigins.split(",");
            for (String origin : origins) {
                config.addAllowedOrigin(origin.trim());
            }
        } else {
            config.addAllowedOrigin(allowedOrigins);
        }
        
        // 允许携带认证信息
        config.setAllowCredentials(true);
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有方法
        config.addAllowedMethod("*");
        // 暴露响应头
        config.addExposedHeader("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "DELETE", "PUT")
        .maxAge(3600);
    }
}