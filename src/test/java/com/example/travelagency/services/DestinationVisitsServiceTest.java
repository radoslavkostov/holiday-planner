package com.example.travelagency.services;

import com.example.travelagency.entities.DestinationVisitsEntity;
import com.example.travelagency.repositories.DestinationVisitsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class DestinationVisitsServiceTest {

    @Mock
    private DestinationVisitsRepository destinationVisitsRepository;

    private DestinationVisitsService destinationVisitsService;

    @BeforeEach
    void setUp(){
        destinationVisitsService = new DestinationVisitsService(destinationVisitsRepository);
    }

    @Test
    void increaseVisitsCount_ShouldIncreaseVisitsCount() {
        Long id = 1L;
        DestinationVisitsEntity destinationVisitsEntity = new DestinationVisitsEntity();
        destinationVisitsEntity.setVisits(1L);
        Optional<DestinationVisitsEntity> optional = Optional.of(destinationVisitsEntity);

        when(destinationVisitsRepository.findByTravelDestinationEntityId(id)).thenReturn(optional);

        destinationVisitsService.increaseVisitsCount(id);
        verify(destinationVisitsRepository, times(1)).save(destinationVisitsEntity);
        assertEquals(2, destinationVisitsEntity.getVisits());
    }

    @Test
    void increaseVisitsCount_ShouldNotIncreaseVisitsCount() {
        Long id = 1L;
        Optional<DestinationVisitsEntity> optional = Optional.empty();

        when(destinationVisitsRepository.findByTravelDestinationEntityId(id)).thenReturn(optional);

        destinationVisitsService.increaseVisitsCount(id);
        verify(destinationVisitsRepository, times(0)).save(any(DestinationVisitsEntity.class));
    }

    @Test
    void findAll_ShouldReturnAllDestinationVisits() {
        List<DestinationVisitsEntity> destinationVisitsEntities = new ArrayList<>();
        destinationVisitsEntities.add(new DestinationVisitsEntity());
        destinationVisitsEntities.add(new DestinationVisitsEntity());

        when(destinationVisitsRepository.findAll()).thenReturn(destinationVisitsEntities);

        List<DestinationVisitsEntity> returnedDestinationVisits = destinationVisitsService.findAll();

        assertEquals(2, returnedDestinationVisits.size());
        verify(destinationVisitsRepository, times(1)).findAll();
    }
}
