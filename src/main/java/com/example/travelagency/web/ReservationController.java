package com.example.travelagency.web;

import com.example.travelagency.models.binding.ReservationAddBindingModel;
import com.example.travelagency.models.binding.TravelDestinationSearchBindingModel;
import com.example.travelagency.models.service.ReservationServiceModel;
import com.example.travelagency.models.service.TravelDestinationServiceModel;
import com.example.travelagency.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final HotelRoomService hotelRoomService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ReservationController(ReservationService reservationService, TravelDestinationService travelDestinationService, HotelService hotelService, HotelRoomService hotelRoomService, UserService userService, ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.travelDestinationService = travelDestinationService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
        this.userService = userService;
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
//
//    @PostMapping("/choose-destination")
//    public String chooseDestination(@Valid TravelDestinationSearchBindingModel travelDestinationSearchBindingModel){
//        return "redirect:/choose-hotel/"+travelDestinationService.findByName(travelDestinationSearchBindingModel.getName()).getId();
//    }

    @GetMapping("/choose-hotel/{id}")
    public String selectHotel(@PathVariable("id") Long id, Model model){
        model.addAttribute("hotels", hotelService.getHotelsByDestination(travelDestinationService.findById(id).getName()));

        return "choose-hotel";
    }

    @GetMapping("/choose-rooms/{id}")
    public String chooseRooms(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){

        model.addAttribute("currentHotel", hotelService.findById(id));
        if (redirectAttributes.containsAttribute("roomAvailability")) {
            model.addAttribute("roomAvailability", redirectAttributes.getFlashAttributes().get("roomAvailability"));
        }
        return "choose-rooms";
    }

    @PostMapping("/search-rooms/{id}")
    public String searchRooms(@PathVariable Long id, @Valid ReservationAddBindingModel reservationAddBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !reservationService.chooseRooms(modelMapper.map(reservationAddBindingModel, ReservationServiceModel.class), id)){
            redirectAttributes.addFlashAttribute("reservationAddBindingModel", reservationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("roomAvailability", false);
            return "redirect:/choose-rooms/"+id;
        }

        return "redirect:/successful-reservation";
    }

    @GetMapping("/successful-reservation")
    public String success(){
        return "successful-reservation";
    }

    @GetMapping("/reservations/{id}")
    public String reservationDetails(@PathVariable Long id, Model model){

        model.addAttribute("reservation", reservationService.findById(id));

        return "reservation-details";
    }


    @PreAuthorize("@reservationService.isOwner(#principal.username, #id)")
    @GetMapping("/delete-reservation/{id}")
    public String deleteReservation(
            @PathVariable("id") Long id) {
        reservationService.deleteById(id);

        return "redirect:/my-reservations";
    }

/*    @GetMapping("/edit-reservation/{id}")
    public String edit(@PathVariable Long id, Model model){

        if(!reservationService.isOwner(userService.getCurrentUser().getEmail(), id)){
            return "redirect:/";
        }

        ReservationEntity reservationEntity = reservationService.findById(id);
        ReservationDTO reservationDTO = modelMapper.map(reservationEntity, ReservationDTO.class);
        reservationDTO.setType(reservationEntity.getHotelRoom().getType());

        model.addAttribute("reservation", reservationDTO);


        return "edit-reservation";
    }

    @PutMapping("/edit-reservation/{id}")
    public String editReservation(@PathVariable Long id, @Valid ReservationDTO reservation){

        reservationService.editRoom(reservation);

        return "redirect:/reservations/"+id;
    }*/


    @ModelAttribute
    public ReservationAddBindingModel reservationAddBindingModel(){
        return new ReservationAddBindingModel();
    }

    @ModelAttribute
    public TravelDestinationSearchBindingModel travelDestinationSearchBindingModel(){
        return new TravelDestinationSearchBindingModel();
    }

}
