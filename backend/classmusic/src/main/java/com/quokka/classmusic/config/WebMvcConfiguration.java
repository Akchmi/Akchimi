package com.quokka.classmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 서버의 모든 경로
        registry.addMapping("/**")
                // 허용 Origin 경로
                .allowedOrigins("*")
                // 허용 메서드
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
}
