package com.example.travelagency.web;

import com.example.travelagency.models.binding.HotelSearchBindingModel;
import com.example.travelagency.models.binding.RatingAddBindingModel;
import com.example.travelagency.models.service.HotelServiceModel;
import com.example.travelagency.models.service.RatingServiceModel;
import com.example.travelagency.models.view.RatingViewModel;
import com.example.travelagency.services.HotelService;
import com.example.travelagency.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;
    private final RatingService ratingService;
    private final ModelMapper modelMapper;

    public HotelController(HotelService hotelService, RatingService ratingService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.ratingService = ratingService;
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

    @ResponseBody
    @GetMapping("/ratings-info/{hotelId}")
    public Object ratingsInfo(@PathVariable Long hotelId, HttpServletRequest request){
        String requestedWith = request.getHeader("X-Requested-With");
        if (!"XMLHttpRequest".equals(requestedWith)) {
            ModelAndView view = new ModelAndView();
            view.setViewName("error");
            return view;
        }

        Double averageRating = ratingService.findAverageRating(hotelId);
        int size = ratingService.findByHotelId(hotelId).size();
        List<Double> list = new ArrayList<>();
        list.add((double) size);
        list.add(averageRating);

        return list;
    }

    @GetMapping("/hotels/{id}")
    public String details(@PathVariable Long id, Model model){

        model.addAttribute("hotel", hotelService.findById(id));

        List<RatingViewModel> ratings = ratingService.findByHotelId(id);

        model.addAttribute("ratings", ratings);
        model.addAttribute("currentUserRating", ratingService.findByUserIdAndHotelId(id));

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

    @ModelAttribute
    public RatingAddBindingModel ratingAddBindingModel(){
        return new RatingAddBindingModel();
    }

}
