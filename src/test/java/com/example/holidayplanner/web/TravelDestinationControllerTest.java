package com.example.holidayplanner.web;

import com.example.holidayplanner.models.binding.TravelDestinationSearchBindingModel;
import com.example.holidayplanner.models.view.TravelDestinationViewModel;
import com.example.holidayplanner.services.FavoriteService;
import com.example.holidayplanner.services.TravelDestinationService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TravelDestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelDestinationService travelDestinationService;

    @MockBean
    private FavoriteService favoriteService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void destinationsTest() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/destinations"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("topDestinations"))
                .andExpect(view().name("destinations"));
    }

    @Test
    void detailsTest() throws Exception {
        Long destinationId = 1L;
        when(favoriteService.checkIfFavorite(anyLong())).thenReturn(true);
        when(travelDestinationService.findById(anyLong())).thenReturn(new TravelDestinationViewModel());
        mockMvc
                .perform(MockMvcRequestBuilders.get("/destinations/"+destinationId))
                .andExpect(model().attributeExists("destination"))
                .andExpect(model().attributeExists("isFavorite"))
                .andExpect(view().name("destination-details"));
    }

    @Test
    void favorTest() throws Exception {
        Long destinationId = 1L;
        mockMvc
                .perform(MockMvcRequestBuilders.post("/favor/"+destinationId).with(csrf()))
                .andExpect(view().name("redirect:/destinations/"+destinationId));
    }

    @Test
    void disfavorTest() throws Exception {
        Long destinationId = 1L;
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/disfavor/"+destinationId).with(csrf()))
                .andExpect(view().name("redirect:/destinations/"+destinationId));
    }

    @Test
    void searchTest_Exception() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/search-destination")
                        .flashAttr("travelDestinationSearchBindingModel", new TravelDestinationSearchBindingModel()))
                .andExpect(model().attributeDoesNotExist("destinations"))
                .andExpect(view().name("search-destination"));
    }

    @Test
    void searchTest_Valid() throws Exception {
        TravelDestinationSearchBindingModel travelDestinationSearchBindingModel = new TravelDestinationSearchBindingModel();
        travelDestinationSearchBindingModel.setName("name");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/search-destination")
                        .flashAttr("travelDestinationSearchBindingModel", travelDestinationSearchBindingModel))
                .andExpect(model().attributeExists("destinations"))
                .andExpect(view().name("search-destination"));
    }
}
