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
            // 特别处理通配符情况
            if ("*".equals(allowedOrigins)) {
                // 当使用通配符时，不能设置allowCredentials为true
                config.addAllowedOriginPattern("*");  // 使用模式匹配代替通配符
                config.setAllowCredentials(false);    // 通配符模式下不允许凭证
            } else {
                config.addAllowedOrigin(allowedOrigins);
                config.setAllowCredentials(true);     // 指定域名时允许凭证
            }
        }
        
        // 通用配置
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
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