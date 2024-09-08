package com.alien.review_my_cv_api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

@Configuration
public class WebConfig {

    private static final List<String> ALLOWED_ORIGINS = Collections.singletonList("*");
    private static final List<String> ALLOWED_HEADERS = Collections.singletonList("*");
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST","DELETE", "PUT", "PATCH");

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = createCorsConfiguration();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private CorsConfiguration createCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(ALLOWED_ORIGINS);
        config.setAllowedHeaders(ALLOWED_HEADERS);
        config.setAllowedMethods(ALLOWED_METHODS);
        return config;
    }
}
