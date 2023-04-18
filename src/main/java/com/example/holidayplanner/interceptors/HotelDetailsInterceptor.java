package com.example.holidayplanner.interceptors;

import com.example.holidayplanner.services.HotelVisitsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HotelDetailsInterceptor implements HandlerInterceptor {

    private final HotelVisitsService hotelVisitsService;

    public HotelDetailsInterceptor(HotelVisitsService hotelVisitsService) {
        this.hotelVisitsService = hotelVisitsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getRequestURI();
        int lastSlashIndex = url.lastIndexOf("/");
        String idString = url.substring(lastSlashIndex + 1);
        Long id = Long.parseLong(idString);
        hotelVisitsService.increaseVisitsCount(id);
        return true;
    }


}
