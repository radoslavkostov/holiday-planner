package com.example.holidayplanner.web;

import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.models.view.ReservationViewModel;
import com.example.holidayplanner.models.view.TravelDestinationViewModel;
import com.example.holidayplanner.services.HotelService;
import com.example.holidayplanner.services.ReservationService;
import com.example.holidayplanner.services.TravelDestinationService;
import com.example.holidayplanner.services.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  UserService userService;

    @MockBean
    private  ReservationService reservationService;

    @MockBean
    private  TravelDestinationService travelDestinationService;

    @MockBean
    private  HotelService hotelService;

    @Autowired
    private  ModelMapper modelMapper;

    @Test
    void homeShouldReturnIndexIfNoCurrentUser() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"));
    }

    @Test
    void homeShouldReturnDestinations() throws Exception {
        when(userService.getCurrentUser()).thenReturn(new UserEntity());
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(view().name("redirect:/destinations"));
    }

    @Test
    void profileShouldReturnMyProfile() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/my-profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("currentUser"))
                .andExpect(view().name("/my-profile"));
    }

    @Test
    void myFavoritesShouldReturnMyFavorites() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        List<TravelDestinationViewModel> travelDestinationViewModels = new ArrayList<>();
        when(travelDestinationService.findByUserId(anyLong())).thenReturn(travelDestinationViewModels);
        mockMvc.perform(MockMvcRequestBuilders.get("/my-favorites"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("favorites"))
                .andExpect(view().name("/my-favorites"));
    }

    @Test
    void ratedHotelsShouldReturnRatedHotels() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        List<HotelViewModel> hotelViewModels = new ArrayList<>();
        when(hotelService.findByUserId(anyLong())).thenReturn(hotelViewModels);
        mockMvc.perform(MockMvcRequestBuilders.get("/rated-hotels"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratedHotels"))
                .andExpect(view().name("/rated-hotels"));
    }

    @Test
    void reservationsShouldReturnMyReservations() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        List<ReservationViewModel> activeReservations = new ArrayList<>();
        List<ReservationViewModel> expiredReservations = new ArrayList<>();
        when(reservationService.findActiveByUserId(anyLong())).thenReturn(activeReservations);
        when(reservationService.findExpiredByUserId(anyLong())).thenReturn(expiredReservations);
        mockMvc.perform(MockMvcRequestBuilders.get("/my-reservations"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeReservations"))
                .andExpect(model().attributeExists("expiredReservations"))
                .andExpect(view().name("/my-reservations"));
    }

    @Test
    void registerShouldReturnAuthRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-register"));
    }

    @Test
    void loginShouldReturnAuthLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-login"));
    }

}
