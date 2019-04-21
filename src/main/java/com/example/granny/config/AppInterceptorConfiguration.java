package com.example.granny.config;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.web.interceptors.FaviconInterceptor;
import com.example.granny.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppInterceptorConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public AppInterceptorConfiguration(TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }
}
