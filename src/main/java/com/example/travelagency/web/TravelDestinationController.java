package com.example.travelagency.web;

import com.example.travelagency.dto.DestinationSearchDTO;
import com.example.travelagency.services.FavoriteService;
import com.example.travelagency.services.TravelDestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TravelDestinationController {

    private final TravelDestinationService travelDestinationService;
    private final FavoriteService favoriteService;

    public TravelDestinationController(TravelDestinationService travelDestinationService, FavoriteService favoriteService) {
        this.travelDestinationService = travelDestinationService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/destinations")
    public String destinations(@Valid DestinationSearchDTO destinationSearchDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model){

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

    @GetMapping("/favor/{id}")
    public String favorite(@PathVariable Long id){
        favoriteService.favor(id);
        return "redirect:/destinations/"+id;
    }

    @GetMapping("/disfavor/{id}")
    public String disfavor(@PathVariable Long id){
        favoriteService.disfavor(id);
        return "redirect:/destinations/"+id;
    }

    @GetMapping("/search-destination")
    public String search(@Valid DestinationSearchDTO destinationSearchDTO, Model model){

        if(!model.containsAttribute("destinationSearchDTO")){
            model.addAttribute("destinationSearchDTO", destinationSearchDTO);
        }

        if(!destinationSearchDTO.isEmpty()){
            model.addAttribute("destinations", travelDestinationService.searchDestinations(destinationSearchDTO));
        }

        return "search-destination";
    }

    @ModelAttribute
    public DestinationSearchDTO destinationSearchDTO() {
        return new DestinationSearchDTO();
    }
}
