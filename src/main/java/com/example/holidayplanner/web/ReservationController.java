package com.example.holidayplanner.web;

import com.example.holidayplanner.models.binding.ReservationAddBindingModel;
import com.example.holidayplanner.models.binding.TravelDestinationSearchBindingModel;
import com.example.holidayplanner.models.service.ReservationServiceModel;
import com.example.holidayplanner.models.service.TravelDestinationServiceModel;
import com.example.holidayplanner.services.*;
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

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final TravelDestinationService travelDestinationService;
    private final HotelService hotelService;
    private final ModelMapper modelMapper;

    public ReservationController(ReservationService reservationService, TravelDestinationService travelDestinationService, HotelService hotelService, ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.travelDestinationService = travelDestinationService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/choose-destination")
    public String get(@Valid TravelDestinationSearchBindingModel travelDestinationSearchBindingModel, Model model,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || travelDestinationSearchBindingModel.isEmpty()){
            redirectAttributes.addFlashAttribute("travelDestinationSearchBindingModel", travelDestinationSearchBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.travelDestinationSearchBindingModel", bindingResult);

            return "choose-destination";
        }

        model.addAttribute("destinations",
                travelDestinationService.searchDestinations(modelMapper.map(travelDestinationSearchBindingModel,
                        TravelDestinationServiceModel.class)));

        return "choose-destination";
    }


    @GetMapping("/choose-hotel/{id}")
    public String selectHotel(@PathVariable("id") Long id, Model model){
        model.addAttribute("currentDestination", travelDestinationService.findById(id));
        model.addAttribute("hotels", hotelService.getHotelsByDestination(travelDestinationService.findById(id).getName()));

        return "choose-hotel";
    }

    @GetMapping("/choose-rooms/{id}")
    public String chooseRooms(@PathVariable("id") Long id, Model model){

        Boolean roomAvailability = (Boolean) model.asMap().get("roomAvailability");
        if (roomAvailability != null) {
            model.addAttribute("roomAvailability", roomAvailability);
        }

        model.addAttribute("currentHotel", hotelService.findById(id));

        return "choose-rooms";
    }

    @PostMapping("/search-rooms/{id}")
    public String searchRooms(@PathVariable Long id, @Valid ReservationAddBindingModel reservationAddBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("reservationAddBindingModel", reservationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationAddBindingModel", bindingResult);
            return "redirect:/choose-rooms/"+id;
        }

        Boolean roomAvailability = reservationService.chooseRooms(modelMapper.map(reservationAddBindingModel, ReservationServiceModel.class), id);

        if(roomAvailability){
            return "redirect:/successful-reservation";
        }
        else{
            redirectAttributes.addFlashAttribute("reservationAddBindingModel", reservationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("roomAvailability", false);
            return "redirect:/choose-rooms/"+id;
        }
    }

    @GetMapping("/successful-reservation")
    public String success(){
        return "successful-reservation";
    }
//
//
//    @PreAuthorize("@reservationService.isOwner(#principal.username, #id)")
//    @GetMapping("/delete-reservation/{id}")
//    public String deleteReservation(
//            @PathVariable("id") Long id) {
//        reservationService.deleteById(id);
//
//        return "redirect:/my-reservations";
//    }

    @ModelAttribute
    public ReservationAddBindingModel reservationAddBindingModel(){
        return new ReservationAddBindingModel();
    }

    @ModelAttribute
    public TravelDestinationSearchBindingModel travelDestinationSearchBindingModel(){
        return new TravelDestinationSearchBindingModel();
    }

}
