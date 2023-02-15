package com.example.travelagency.services;

import com.example.travelagency.entities.TravelDestinationEntity;
import com.example.travelagency.models.service.TravelDestinationServiceModel;
import com.example.travelagency.models.view.TravelDestinationViewModel;
import com.example.travelagency.repositories.TravelDestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelDestinationServiceTest {

    @Mock
    private TravelDestinationRepository travelDestinationRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private TravelDestinationService travelDestinationService;

    @BeforeEach
    void setUp() {
        travelDestinationService = new TravelDestinationService(travelDestinationRepository, modelMapper);
    }

    @Test
    void findById_ShouldReturnTravelDestination() {
        TravelDestinationEntity travelDestinationEntity = new TravelDestinationEntity();
        travelDestinationEntity.setId(1L);

        when(travelDestinationRepository.findById(anyLong())).thenReturn(Optional.of(travelDestinationEntity));

        assertEquals(travelDestinationService.findById(1L), modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class));
    }

    @Test
    void findById_ShouldReturnNullIfDoesntExist() {
        when(travelDestinationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(travelDestinationService.findById(1L));
    }

    @Test
    void searchDestinations_ShouldReturnListOfTravelDestinations() {
        TravelDestinationEntity travelDestinationEntity = new TravelDestinationEntity();
        List<TravelDestinationEntity> travelDestinationEntities = Collections.singletonList(travelDestinationEntity);
        TravelDestinationViewModel travelDestinationViewModel = modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class);
        List<TravelDestinationViewModel> travelDestinationViewModels = Collections.singletonList(travelDestinationViewModel);
        when(travelDestinationRepository.findTravelDestinationEntitiesByNameContains(anyString())).thenReturn(travelDestinationEntities);
        TravelDestinationServiceModel travelDestinationServiceModel = new TravelDestinationServiceModel();
        travelDestinationServiceModel.setName("name");
        assertEquals(travelDestinationService.searchDestinations(travelDestinationServiceModel), travelDestinationViewModels);
    }

    @Test
    void getTopFavoriteDestinations_ShouldReturnListOfTravelDestinations() {
        TravelDestinationEntity travelDestinationEntity = new TravelDestinationEntity();
        List<TravelDestinationEntity> travelDestinationEntities = Collections.singletonList(travelDestinationEntity);
        TravelDestinationViewModel travelDestinationViewModel = modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class);
        List<TravelDestinationViewModel> travelDestinationViewModels = Collections.singletonList(travelDestinationViewModel);
        when(travelDestinationRepository.findTopFavoriteDestinations()).thenReturn(travelDestinationEntities);
        assertEquals(travelDestinationService.getTopFavoriteDestinations(), travelDestinationViewModels);
    }

    @Test
    void findByUserId_ShouldReturnTravelDestination() {
        TravelDestinationEntity travelDestinationEntity = new TravelDestinationEntity();
        List<TravelDestinationEntity> travelDestinationEntities = Collections.singletonList(travelDestinationEntity);
        TravelDestinationViewModel travelDestinationViewModel = modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class);
        List<TravelDestinationViewModel> travelDestinationViewModels = Collections.singletonList(travelDestinationViewModel);
        when(travelDestinationRepository.findByUserId(anyLong())).thenReturn(travelDestinationEntities);
        assertEquals(travelDestinationService.findByUserId(1L), travelDestinationViewModels);
    }
}
