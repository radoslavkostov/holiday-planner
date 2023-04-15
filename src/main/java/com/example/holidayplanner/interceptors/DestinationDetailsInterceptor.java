package com.example.holidayplanner.interceptors;

import com.example.holidayplanner.services.DestinationVisitsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DestinationDetailsInterceptor implements HandlerInterceptor {

    private final DestinationVisitsService destinationVisitsService;

    public DestinationDetailsInterceptor(DestinationVisitsService destinationVisitsService) {
        this.destinationVisitsService = destinationVisitsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        int lastSlashIndex = url.lastIndexOf("/");
        String idString = url.substring(lastSlashIndex + 1);
        Long id = Long.parseLong(idString);
        destinationVisitsService.increaseVisitsCount(id);
        return true;
    }
}
