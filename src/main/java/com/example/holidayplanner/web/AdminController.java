package com.example.holidayplanner.web;

import com.example.holidayplanner.models.binding.UserSearchBindingModel;
import com.example.holidayplanner.models.service.UserServiceModel;
import com.example.holidayplanner.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin-panel")
    public String adminPanel(@Valid UserSearchBindingModel userSearchBindingModel, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!userService.hasRole("ADMIN", userService.getCurrentUser().getId())){
            return "redirect:/";
        }
        if(bindingResult.hasErrors() || userSearchBindingModel.isEmpty()){
            redirectAttributes.addFlashAttribute("userSearchBindingModel", userSearchBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSearchBindingModel", bindingResult);

            return "admin-panel";
        }

        model.addAttribute("users", userService.findByEmail(modelMapper.map(userSearchBindingModel, UserServiceModel.class)));

        return "admin-panel";
    }

    @GetMapping("/user-details/{id}")
    public String userDetails(@PathVariable Long id, Model model){
        if(!userService.hasRole("ADMIN", userService.getCurrentUser().getId())){
            return "redirect:/";
        }

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("isModerator", userService.hasRole("MODERATOR", id));

        return "user-details";
    }

    @PostMapping("/grant-mod/{id}")
    public String grantMod(@PathVariable Long id){
        if(!userService.hasRole("ADMIN", userService.getCurrentUser().getId())){
            return "redirect:/";
        }
        userService.grantMod(id);
        return "redirect:/user-details/"+id;
    }

    @DeleteMapping("/revoke-mod/{id}")
    public String revokeMod(@PathVariable Long id){
        if(!userService.hasRole("ADMIN", userService.getCurrentUser().getId())){
            return "redirect:/";
        }
        userService.revokeMod(id);
        return "redirect:/user-details/"+id;
    }


}
