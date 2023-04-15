package com.example.holidayplanner.config;

import com.example.holidayplanner.interceptors.DestinationDetailsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final DestinationDetailsInterceptor destinationDetailsInterceptor;

    public InterceptorConfiguration(DestinationDetailsInterceptor destinationDetailsInterceptor) {
        this.destinationDetailsInterceptor = destinationDetailsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(destinationDetailsInterceptor)
                .addPathPatterns("/destinations/{id}");
    }
}
