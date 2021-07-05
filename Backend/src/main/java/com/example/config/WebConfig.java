package com.example.config;

import com.example.Application;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE")
                .allowedHeaders("*")
                .allowedOrigins("http://localhost:" + Application.APP_PORT)
//                .allowedOrigins("*") // For debugging
                .allowCredentials(true)
                .maxAge(3600);
    }
}
