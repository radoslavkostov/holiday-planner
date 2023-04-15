package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelEntity;
import com.example.holidayplanner.models.service.HotelServiceModel;
import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.repositories.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private HotelService hotelService;

    @BeforeEach
    void setUp(){
        hotelService = new HotelService(hotelRepository, modelMapper);
    }

    @Test
    void getHotelsByDestination_ShouldReturnListOfHotels() {
        List<HotelEntity> hotelEntities = new ArrayList<>();
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntities.add(hotelEntity);
        List<HotelViewModel> hotelViewModels = new ArrayList<>();
        HotelViewModel hotelViewModel = modelMapper.map(hotelEntity, HotelViewModel.class);
        hotelViewModels.add(hotelViewModel);

        when(hotelRepository.findByTravelDestination(anyString())).thenReturn(hotelEntities);

        List<HotelViewModel> result = hotelService.getHotelsByDestination(anyString());

        assertEquals(hotelViewModels, result);
    }

    @Test
    void searchHotels_ShouldReturnListOfHotels() {
        List<HotelEntity> hotelEntities = new ArrayList<>();
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntities.add(hotelEntity);
        List<HotelViewModel> hotelViewModels = new ArrayList<>();
        HotelViewModel hotelViewModel = modelMapper.map(hotelEntity, HotelViewModel.class);
        hotelViewModels.add(hotelViewModel);

        HotelServiceModel hotelServiceModel = new HotelServiceModel();
        hotelServiceModel.setName("name");

        when(hotelRepository.searchHotels("name", "name")).thenReturn(hotelEntities);
        assertEquals(hotelViewModels, hotelService.searchHotels(hotelServiceModel));
    }

    @Test
    void findById_ShouldReturnHotel() {
        HotelEntity hotelEntity = new HotelEntity();
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));
        assertEquals(hotelService.findById(1L), modelMapper.map(hotelEntity, HotelViewModel.class));
    }

    @Test
    void findByUserId_ShouldReturnListOfHotels() {
        HotelEntity hotelEntity = new HotelEntity();
        List<HotelEntity> hotelEntities = new ArrayList<>();
        hotelEntities.add(hotelEntity);
        when(hotelRepository.findByUserId(anyLong())).thenReturn(hotelEntities);
        List<HotelViewModel> hotelViewModels = new ArrayList<>();
        HotelViewModel hotelViewModel = modelMapper.map(hotelEntity, HotelViewModel.class);
        hotelViewModels.add(hotelViewModel);
        assertEquals(hotelService.findByUserId(1L), hotelViewModels);
    }
}
