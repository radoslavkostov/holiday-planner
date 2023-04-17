package com.example.holidayplanner.web;

import com.example.holidayplanner.models.binding.UserEditBindingModel;
import com.example.holidayplanner.models.binding.UserRegisterBindingModel;
import com.example.holidayplanner.models.service.UserServiceModel;
import com.example.holidayplanner.services.HotelService;
import com.example.holidayplanner.services.ReservationService;
import com.example.holidayplanner.services.TravelDestinationService;
import com.example.holidayplanner.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final TravelDestinationService travelDestinationService;
    private final HotelService hotelService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ReservationService reservationService, TravelDestinationService travelDestinationService, HotelService hotelService, ModelMapper modelMapper) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.travelDestinationService = travelDestinationService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String home(){
        if(userService.getCurrentUser()!=null){
            return "redirect:/destinations";
        }
        else return "/index";
    }

    @GetMapping("/my-profile")
    public String profile(Model model){
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "/my-profile";
    }

    @PutMapping("/edit-username/{id}")
    public String editUsername(@PathVariable Long id, @Valid UserEditBindingModel userEditBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(!id.equals(userService.getCurrentUser().getId())){
            return "redirect:/destinations";
        }

        if(bindingResult.hasErrors() || userEditBindingModel.isEmpty()){
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel", bindingResult);

            return "redirect:/my-profile";
        }

        userService.editUsername(userEditBindingModel, id);

        return "redirect:/my-profile";

    }

    @GetMapping("/my-favorites")
    public String myFavorites(Model model){
        model.addAttribute("favorites", travelDestinationService.findByUserId(userService.getCurrentUser().getId()));
        return "/my-favorites";
    }

    @GetMapping("/rated-hotels")
    public String rated(Model  model){
        model.addAttribute("ratedHotels", hotelService.findByUserId(userService.getCurrentUser().getId()));
        return "/rated-hotels";
    }

    @GetMapping("my-reservations")
    public String reservations(Model model){

        model.addAttribute("activeReservations", reservationService.findActiveByUserId(userService.getCurrentUser().getId()));
        model.addAttribute("expiredReservations", reservationService.findExpiredByUserId(userService.getCurrentUser().getId()));

        return "/my-reservations";
    }

    @GetMapping("/users/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()||!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }
        userService.registerAndLogin(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:/";
    }

    @GetMapping("/users/login")
    public String login() {
        return "auth-login";
    }

    @PostMapping("/users/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
        redirectAttributes.addFlashAttribute("bad_credentials",
                true);

        return "redirect:/users/login";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserEditBindingModel userEditBindingModel(){
        return new UserEditBindingModel();
    }
}
