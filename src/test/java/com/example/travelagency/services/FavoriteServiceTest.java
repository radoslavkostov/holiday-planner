package com.example.travelagency.services;

import com.example.travelagency.entities.FavoriteEntity;
import com.example.travelagency.entities.UserEntity;
import com.example.travelagency.models.view.TravelDestinationViewModel;
import com.example.travelagency.repositories.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserService userService;

    @Mock
    private TravelDestinationService travelDestinationService;

    private ModelMapper modelMapper = new ModelMapper();

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp(){
        favoriteService = new FavoriteService(favoriteRepository, userService, travelDestinationService, modelMapper);
    }

    @Test
    public void checkIfFavorite_ShouldReturnTrueIfExistsInRepository() {
        when(favoriteRepository.findByTravelDestinationIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(new FavoriteEntity()));
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);

        boolean result = favoriteService.checkIfFavorite(1L);

        assertTrue(result);
    }

    @Test
    public void checkIfFavorite_ShouldReturnFalseIfNotExistsInRepository() {
        when(favoriteRepository.findByTravelDestinationIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);

        boolean result = favoriteService.checkIfFavorite(1L);

        assertFalse(result);
    }

    @Test
    public void favor_ShouldAddToRepository() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(travelDestinationService.findById(anyLong())).thenReturn(new TravelDestinationViewModel());

        favoriteService.favor(1L);

        verify(favoriteRepository, times(1)).save(any(FavoriteEntity.class));
    }

    @Test
    public void disfavor_ShouldDeleteFromRepository() {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        when(favoriteRepository.findByTravelDestinationIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(favoriteEntity));
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);

        favoriteService.disfavor(1L);

        verify(favoriteRepository, times(1)).delete(favoriteEntity);
    }
}

