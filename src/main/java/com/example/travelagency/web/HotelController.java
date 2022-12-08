package com.example.travelagency.web;

import com.example.travelagency.dto.HotelSearchDTO;
import com.example.travelagency.dto.RatingDTO;
import com.example.travelagency.entities.RatingEntity;
import com.example.travelagency.services.HotelService;
import com.example.travelagency.services.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;
    private final RatingService ratingService;
    private Long hotelId;

    public HotelController(HotelService hotelService, RatingService ratingService) {
        this.hotelService = hotelService;
        this.ratingService = ratingService;
    }

    @GetMapping("/hotels")
    public String hotels(@Valid HotelSearchDTO hotelSearchDTO, Model model){
        if(!hotelSearchDTO.isEmpty()){
            model.addAttribute("hotels", hotelService.searchHotels(hotelSearchDTO));
        }

        return "hotels";
    }

    @GetMapping("/hotels/{id}")
    public String details(@PathVariable Long id, Model model){

        model.addAttribute("hotel", hotelService.findById(id));
        List<RatingEntity> ratings = ratingService.findByHotelId(id);
        model.addAttribute("ratings", ratings);
        model.addAttribute("averageRating", ratingService.findAverageRating(id));
        model.addAttribute("ratingsCount", ratings.size());
        hotelId=id;

        return "hotel-details";
    }

    @PostMapping("/add-rating")
    public String rate(@Valid RatingDTO ratingDTO){
        String redirect = "redirect:/hotels/"+hotelId;

        ratingDTO.setHotelId(hotelId);
        ratingService.addRating(ratingDTO);

        return redirect;
    }

    @ModelAttribute
    public HotelSearchDTO hotelSearchDTO(){
        return new HotelSearchDTO();
    }

    @ModelAttribute
    public RatingDTO ratingDTO(){
        return new RatingDTO();
    }

}
