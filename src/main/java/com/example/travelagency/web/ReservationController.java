package com.example.travelagency.web;

import com.example.travelagency.dto.ReservationBindingModel;
import com.example.travelagency.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/create-reservation")
    public String create(){
        return "create-reservation";
    }

    @PostMapping("/create-reservation")
    public String createReservation(@Valid ReservationBindingModel reservationBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reservationBindingModel", reservationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationBindingModel", bindingResult);

            return "redirect:create-reservation";
        }

        reservationService.createReservation(reservationBindingModel);

        return "redirect:create-reservation";
    }

    @ModelAttribute
    public ReservationBindingModel reservationBindingModel(){
        return new ReservationBindingModel();
    }
}
