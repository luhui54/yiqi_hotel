package com.yiqi.api_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置【【【 终极调试版 】】】
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. 允许发送凭证，这是必须的
        config.setAllowCredentials(true);

        // 2. 【【核心调试代码】】我们不再从环境变量读取，直接写死允许的来源
        // 我们把生产环境和本地环境都加上，确保万无一失
        config.addAllowedOrigin("https://yiqi-hotel.vercel.app");
        config.addAllowedOrigin("http://localhost:3000"); // 加上一个常见的本地前端端口
        config.addAllowedOrigin("http://localhost:5173"); // Vite 默认端口

        // 3. 允许所有请求头
        config.addAllowedHeader("*");

        // 4. 允许所有请求方法
        config.addAllowedMethod("*");

        // 5. 将此配置应用到所有路径
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}