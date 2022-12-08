package com.example.travelagency.web;

import com.example.travelagency.dto.DestinationSearchDTO;
import com.example.travelagency.dto.ReservationDTO;
import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.services.*;
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
public class ReservationController {

    private final ReservationService reservationService;
    private final TravelDestinationService travelDestinationService;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;
    private final UserService userService;
    private Long hotelId;

    public ReservationController(ReservationService reservationService, TravelDestinationService travelDestinationService, HotelService hotelService, HotelRoomService hotelRoomService, UserService userService) {
        this.reservationService = reservationService;
        this.travelDestinationService = travelDestinationService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
        this.userService = userService;
    }

    @GetMapping("/create-reservation")
    public String get(Model model){

        model.addAttribute("destinations", travelDestinationService.findAll());

//        if(!model.containsAttribute("destinationSearchModel")) {
//            model.addAttribute("destinationSearchModel", destinationSearchDTO);
//        }
//        System.out.println(destinationSearchDTO.getName());
//        if(!destinationSearchDTO.isEmpty()){
//            System.out.println("blablablablabl");
//            List<HotelEntity> hotels = hotelService.getHotelsByDestination(destinationSearchDTO);
//            model.addAttribute("hotels", hotels);
//            hotels.forEach(h -> System.out.println(h.getName()));
//        }
        return "create-reservation";
    }

    @PostMapping("/create-reservation")
    public String chooseDestination(@Valid DestinationSearchDTO destinationSearchDTO){
        String result = "redirect:/select-hotel/"+travelDestinationService.findByName(destinationSearchDTO.getName()).getId();
        return result;
    }

    @GetMapping("/select-hotel/{id}")
    public String selectHotel(@PathVariable("id") Long id, Model model){
        model.addAttribute("hotels", hotelService.getHotelsByDestination(travelDestinationService.findById(id)));

        return "select-hotel";
    }

    @GetMapping("/choose-rooms/{id}")
    public String chooseRooms(@PathVariable("id") Long id){

        hotelId=id;

        return "choose-rooms";
    }

    @PostMapping("/search-rooms")
    public String searchRooms(@Valid ReservationDTO reservationDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){

        if(reservationService.chooseRooms(reservationDTO, hotelId)){
            return "redirect:/successful-reservation";
        }

        redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationDTO", bindingResult);
        return "redirect:/choose-rooms/"+hotelId;
    }

    @GetMapping("/successful-reservation")
    public String success(){
        return "successful-reservation";
    }

    @ModelAttribute
    public ReservationDTO reservationDTO(){
        return new ReservationDTO();
    }

    @ModelAttribute
    public DestinationSearchDTO destinationSearchDTO(){
        return new DestinationSearchDTO();
    }

}
