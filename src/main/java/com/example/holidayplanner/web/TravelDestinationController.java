package com.example.holidayplanner.web;

import com.example.holidayplanner.models.binding.TravelDestinationSearchBindingModel;
import com.example.holidayplanner.models.service.TravelDestinationServiceModel;
import com.example.holidayplanner.services.FavoriteService;
import com.example.holidayplanner.services.TravelDestinationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TravelDestinationController {

    private final TravelDestinationService travelDestinationService;
    private final FavoriteService favoriteService;
    private final ModelMapper modelMapper;

    public TravelDestinationController(TravelDestinationService travelDestinationService, FavoriteService favoriteService, ModelMapper modelMapper) {
        this.travelDestinationService = travelDestinationService;
        this.favoriteService = favoriteService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/destinations")
    public String destinations(Model model){

        model.addAttribute("topDestinations", travelDestinationService.getTopFavoriteDestinations());

        return "destinations";
    }

    @GetMapping("/destinations/{id}")
    public String details(@PathVariable Long id, Model model){

        boolean isFavorite = favoriteService.checkIfFavorite(id);

        model.addAttribute("destination", travelDestinationService.findById(id));
        model.addAttribute("isFavorite", isFavorite);

        return "destination-details";
    }

    @PostMapping("/favor/{id}")
    public String favorite(@PathVariable Long id){
        favoriteService.favor(id);
        return "redirect:/destinations/"+id;
    }

    @DeleteMapping("/disfavor/{id}")
    public String disfavor(@PathVariable Long id){
        favoriteService.disfavor(id);
        return "redirect:/destinations/"+id;
    }

    @GetMapping("/search-destination")
    public String search(@Valid TravelDestinationSearchBindingModel travelDestinationSearchBindingModel, Model model,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || travelDestinationSearchBindingModel.isEmpty()){
            redirectAttributes.addFlashAttribute("travelDestinationSearchBindingModel", travelDestinationSearchBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.travelDestinationSearchBindingModel", bindingResult);

            return "search-destination";
        }

        model.addAttribute("destinations",
                travelDestinationService.searchDestinations(modelMapper.map(travelDestinationSearchBindingModel,
                        TravelDestinationServiceModel.class)));

        return "search-destination";
    }

    @ModelAttribute
    public TravelDestinationSearchBindingModel travelDestinationSearchBindingModel() {
        return new TravelDestinationSearchBindingModel();
    }
}
