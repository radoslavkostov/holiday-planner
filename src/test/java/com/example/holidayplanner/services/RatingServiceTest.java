package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.RatingEntity;
import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.models.service.RatingServiceModel;
import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.models.view.RatingViewModel;
import com.example.holidayplanner.repositories.RatingRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private HotelService hotelService;

    @Mock
    private UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();

    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        ratingService = new RatingService(ratingRepository, hotelService, userService, modelMapper);
    }

    @Test
    void addRatingTest_whenRatingExists_thenUpdateRating() {
        RatingServiceModel ratingServiceModel = new RatingServiceModel();
        ratingServiceModel.setValue(3);
        ratingServiceModel.setReview("Review 1");

        Long hotelId = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setValue(1);
        ratingEntity.setReview("Review 2");

        when(ratingRepository.findByUserIdAndHotelId(userEntity.getId(), hotelId)).thenReturn(Optional.of(ratingEntity));

        ratingService.addRating(ratingServiceModel, hotelId);

        verify(ratingRepository, times(1)).save(ratingEntity);
        assertEquals(ratingServiceModel.getValue(), ratingEntity.getValue());
        assertEquals(ratingServiceModel.getReview(), ratingEntity.getReview());
    }

    @Test
    void addRatingTest_whenRatingDoesNotExist_thenAddRating() {
        Long hotelId = 1L;
        RatingServiceModel ratingServiceModel = new RatingServiceModel();
        ratingServiceModel.setValue(4);
        ratingServiceModel.setReview("Great hotel!");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);

        HotelViewModel hotelViewModel = new HotelViewModel();
        when(hotelService.findById(hotelId)).thenReturn(hotelViewModel);

        RatingEntity existingRatingEntity = new RatingEntity();
        when(ratingRepository.findByUserIdAndHotelId(userEntity.getId(), hotelId)).thenReturn(Optional.of(existingRatingEntity));

        when(ratingRepository.findByUserIdAndHotelId(userEntity.getId(), hotelId)).thenReturn(Optional.empty());

        RatingService ratingService = new RatingService(ratingRepository, hotelService, userService, modelMapper);

        ratingService.addRating(ratingServiceModel, hotelId);

        verify(ratingRepository, times(1)).save(any(RatingEntity.class));
    }

    @Test
    void findByHotelIdTest() {
        Long hotelId = 1L;

        List<RatingEntity> ratingEntities = new ArrayList<>();
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntities.add(ratingEntity);
        when(ratingRepository.findByHotelId(hotelId)).thenReturn(ratingEntities);

        RatingViewModel ratingViewModel = new RatingViewModel();

        List<RatingViewModel> ratingViewModels = ratingService.findByHotelId(hotelId);

        verify(ratingRepository, times(1)).findByHotelId(hotelId);
        assertEquals(1, ratingViewModels.size());
        assertEquals(ratingViewModel, ratingViewModels.get(0));
    }

    @Test
    void findAverageRating_ShouldReturnDouble(){
        Double averageRating = 4.3D;
        when(ratingRepository.findAverageRating(anyLong())).thenReturn(averageRating);
        assertEquals(ratingService.findAverageRating(1L), averageRating);
    }

    @Test
    void findByUserIdAndHotelIdTest() {
        Long hotelId = 1L;
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setId(1L);
        ratingEntity.setValue(4);
        ratingEntity.setReview("Review");

        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(ratingRepository.findByUserIdAndHotelId(userId, hotelId)).thenReturn(Optional.of(ratingEntity));

        RatingViewModel result = ratingService.findByUserIdAndHotelId(hotelId);

        verify(ratingRepository, times(1)).findByUserIdAndHotelId(userId, hotelId);
        assertEquals(ratingEntity.getValue(), result.getValue());
        assertEquals(ratingEntity.getReview(), result.getReview());
    }

}
