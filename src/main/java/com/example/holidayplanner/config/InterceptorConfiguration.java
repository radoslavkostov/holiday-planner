package com.example.holidayplanner.config;

import com.example.holidayplanner.interceptors.DestinationDetailsInterceptor;
import com.example.holidayplanner.interceptors.HotelDetailsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final DestinationDetailsInterceptor destinationDetailsInterceptor;
    private final HotelDetailsInterceptor hotelDetailsInterceptor;

    public InterceptorConfiguration(DestinationDetailsInterceptor destinationDetailsInterceptor, HotelDetailsInterceptor hotelDetailsInterceptor) {
        this.destinationDetailsInterceptor = destinationDetailsInterceptor;
        this.hotelDetailsInterceptor = hotelDetailsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(destinationDetailsInterceptor)
                .addPathPatterns("/destinations/{id}");
        registry.addInterceptor(hotelDetailsInterceptor)
                .addPathPatterns("/hotels/{id}");
    }
}
