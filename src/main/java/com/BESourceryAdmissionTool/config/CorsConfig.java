package com.BESourceryAdmissionTool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${cors.allowOrigin:http://localhost:3000}")
    private String allowOrigin;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                if (registry != null) {
                    registry.addMapping("/**").allowedMethods("POST", "PUT", "GET", "DELETE", "HEAD")
                            .allowedOrigins(allowOrigin).exposedHeaders("authorization");
                }
            }
        };
    }

}
