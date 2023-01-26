package com.example.travelagency.web;

import com.example.travelagency.models.binding.HotelSearchBindingModel;
import com.example.travelagency.models.binding.RatingAddBindingModel;
import com.example.travelagency.models.service.HotelServiceModel;
import com.example.travelagency.models.service.RatingServiceModel;
import com.example.travelagency.models.view.RatingViewModel;
import com.example.travelagency.services.HotelService;
import com.example.travelagency.services.RatingService;
import com.example.travelagency.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;
    private final RatingService ratingService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public HotelController(HotelService hotelService, RatingService ratingService, UserService userService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.ratingService = ratingService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/hotels")
    public String hotels(@Valid HotelSearchBindingModel hotelSearchBindingModel, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()|| hotelSearchBindingModel.isEmpty()){
            redirectAttributes.addFlashAttribute("hotelSearchBindingModel", hotelSearchBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hotelSearchBindingModel", bindingResult);

            return "hotels";
        }

        model.addAttribute("hotels", hotelService.searchHotels(modelMapper.map(hotelSearchBindingModel, HotelServiceModel.class)));

        return "hotels";
    }

    @GetMapping("/hotels/{id}")
    public String details(@PathVariable Long id, Model model){

        model.addAttribute("hotel", hotelService.findById(id));

        List<RatingViewModel> ratings = ratingService.findByHotelId(id);

        model.addAttribute("ratings", ratings);
        model.addAttribute("currentUserRating", ratingService.findByUserIdAndHotelId(id));
        model.addAttribute("averageRating", ratingService.findAverageRating(id));
        model.addAttribute("ratingsCount", ratings.size());

        return "hotel-details";
    }

    @PostMapping("/add-rating/{id}")
    public String rate(@PathVariable Long id, @Valid RatingAddBindingModel ratingAddBindingModel,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes){

        String redirect = "redirect:/hotels/"+id;

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("ratingAddBindingModel", ratingAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ratingAddBindingModel", bindingResult);

            return redirect;
        }

        ratingService.addRating(modelMapper.map(ratingAddBindingModel, RatingServiceModel.class), id);

        return redirect;
    }

//    @ModelAttribute
//    public HotelSearchBindingModel hotelSearchBindingModel(){
//        return new HotelSearchBindingModel();
//    }

    @ModelAttribute
    public RatingAddBindingModel ratingAddBindingModel(){
        return new RatingAddBindingModel();
    }

}
