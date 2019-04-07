package com.example.granny.config;

import com.example.granny.constants.GlobalConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppInterceptorConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(homeInterceptor())
//                .addPathPatterns(GlobalConstants.URL_USER_HOME);
//    }
//
//    @Bean
//    public HomeInterceptor homeInterceptor() {
//        return new HomeInterceptor();
//    }
}
