package com.example.travelagency.init;

import com.example.travelagency.services.HotelRoomService;
import com.example.travelagency.services.HotelService;
import com.example.travelagency.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;

    public AppInit(UserService userService, HotelService hotelService, HotelRoomService hotelRoomService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
        hotelService.init();
        hotelRoomService.init();
    }
}
