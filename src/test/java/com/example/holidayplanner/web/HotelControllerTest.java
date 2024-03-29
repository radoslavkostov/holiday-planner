package com.example.holidayplanner.web;

import com.example.holidayplanner.entities.TravelDestinationEntity;
import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.models.binding.HotelSearchBindingModel;
import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.models.view.RatingViewModel;
import com.example.holidayplanner.services.HotelService;
import com.example.holidayplanner.services.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private RatingService ratingService;


    @Test
    void hotelsTest_Error() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/hotels").flashAttr("hotelSearchBindingModel", new HotelSearchBindingModel()))
                .andExpect(view().name("hotels"))
                .andExpect(model().attributeDoesNotExist("hotels"));
    }

    @Test
    void hotelsTest_ShouldReturnHotelsPageWithHotelsAttribute() throws Exception {
        HotelSearchBindingModel hotelSearchBindingModel = new HotelSearchBindingModel();
        hotelSearchBindingModel.setName("name");
        mockMvc
                .perform(MockMvcRequestBuilders.get("/hotels").flashAttr("hotelSearchBindingModel", hotelSearchBindingModel))
                .andExpect(view().name("hotels"))
                .andExpect(model().attributeExists("hotels"));
    }

    @Test
    void detailsTest() throws Exception {
        Long hotelId = 1L;
        HotelViewModel hotelViewModel = new HotelViewModel();
        TravelDestinationEntity travelDestinationEntity = new TravelDestinationEntity();
        travelDestinationEntity.setId(1L);
        hotelViewModel.setTravelDestination(travelDestinationEntity);
        List<RatingViewModel> ratings = new ArrayList<>();
        RatingViewModel ratingViewModel = new RatingViewModel();
        ratingViewModel.setReview("review");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("name");
        userEntity.setLastName("name");
        ratingViewModel.setUser(userEntity);
        ratings.add(ratingViewModel);
        when(hotelService.findById(anyLong())).thenReturn(hotelViewModel);
        when(ratingService.findByHotelId(anyLong())).thenReturn(ratings);
        when(ratingService.findByUserIdAndHotelId(anyLong())).thenReturn(ratingViewModel);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/hotels/"+hotelId))
                .andExpect(model().attributeExists("hotel"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attributeExists("currentUserRating"))
                .andExpect(view().name("hotel-details"));
    }

    @Test
    public void testRatingsInfoReturnsAverageRatingAndSizeOfRatingsForValidRequest() throws Exception {
        Long hotelId = 1L;
        int size = 5;
        double averageRating = 4.0;
        List<Double> expectedResult = new ArrayList<>();
        expectedResult.add((double) size);
        expectedResult.add(averageRating);

        when(ratingService.findAverageRating(hotelId)).thenReturn(averageRating);
        when(ratingService.findByHotelId(hotelId)).thenReturn(Collections.nCopies(size, new RatingViewModel()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ratings-info/" + hotelId)
                .header("X-Requested-With", "XMLHttpRequest"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<Double> actualResult = new ObjectMapper().readValue(responseBody, List.class);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRatingsInfoReturnsErrorViewForNonAjaxRequest() throws Exception {
        Long hotelId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ratings-info/" + hotelId))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertEquals("error", modelAndView.getViewName());
    }

}
