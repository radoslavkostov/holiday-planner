package com.example.travelagency.web;

import com.example.travelagency.models.view.TravelDestinationViewModel;
import com.example.travelagency.services.HotelService;
import com.example.travelagency.services.ReservationService;
import com.example.travelagency.services.TravelDestinationService;
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
    private ReservationService reservationService;

    @MockBean
    private TravelDestinationService travelDestinationService;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

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
}
