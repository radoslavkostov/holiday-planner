package com.example.travelagency.init;

import com.example.travelagency.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;
    private final ArticleService articleService;
    private final TravelDestinationService travelDestinationService;
    private final ForumService forumService;

    public AppInit(UserService userService, HotelService hotelService, HotelRoomService hotelRoomService, ArticleService articleService, TravelDestinationService travelDestinationService, ForumService forumService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
        this.articleService = articleService;
        this.travelDestinationService = travelDestinationService;
        this.forumService = forumService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
        hotelService.init();
        hotelRoomService.init();
        articleService.init();
        travelDestinationService.init();
        forumService.init();
    }
}
