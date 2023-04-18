package com.example.holidayplanner.web;

import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.models.view.TravelDestinationViewModel;
import com.example.holidayplanner.services.HotelService;
import com.example.holidayplanner.services.TravelDestinationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelDestinationService travelDestinationService;

    @MockBean
    private HotelService hotelService;

    @Mock
    private Model model;

    @Test
    void selectHotelShouldReturnChooseHotel() throws Exception {
        TravelDestinationViewModel travelDestinationViewModel = new TravelDestinationViewModel();
        travelDestinationViewModel.setName("name");
        when(travelDestinationService.findById(anyLong())).thenReturn(travelDestinationViewModel);
        when(hotelService.getHotelsByDestination(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/choose-hotel/"+1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("currentDestination"))
                .andExpect(model().attributeExists("hotels"))
                .andExpect(view().name("choose-hotel"));

    }

    @Test
    void chooseRoomsShouldAddRoomAvailabilityAsTrueIfFlashAttributeExists() throws Exception {
        when(model.asMap()).thenReturn(Map.of("roomAvailability", true));
        when(hotelService.findById(anyLong())).thenReturn(new HotelViewModel());
        mockMvc.perform(MockMvcRequestBuilders.get("/choose-rooms/"+1L).flashAttr("roomAvailability", true))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roomAvailability"))
                .andExpect(model().attributeExists("currentHotel"))
                .andExpect(view().name("choose-rooms"));

    }

    @Test
    void successfulReservationShouldReturnSuccessfulReservationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/successful-reservation"))
                .andExpect(status().isOk())
                .andExpect(view().name("successful-reservation"));
    }

}
