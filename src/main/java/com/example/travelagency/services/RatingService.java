package com.example.travelagency.services;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.RatingEntity;
import com.example.travelagency.models.service.RatingServiceModel;
import com.example.travelagency.models.view.RatingViewModel;
import com.example.travelagency.repositories.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final HotelService hotelService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RatingService(RatingRepository ratingRepository, HotelService hotelService, UserService userService, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.hotelService = hotelService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addRating(RatingServiceModel ratingServiceModel, Long hotelId) {
        Optional<RatingEntity> optional = ratingRepository.findByUserIdAndHotelId(userService.getCurrentUser().getId(), hotelId);
        if(optional.isPresent()){
            RatingEntity updatedRating = optional.get();
            updatedRating.setValue(ratingServiceModel.getValue());
            updatedRating.setReview(ratingServiceModel.getReview());
            ratingRepository.save(updatedRating);
        }
        else{
            RatingEntity newRating = modelMapper.map(ratingServiceModel, RatingEntity.class);
            newRating.setUser(userService.getCurrentUser());
            newRating.setHotel(modelMapper.map(hotelService.findById(hotelId), HotelEntity.class));
            ratingRepository.save(newRating);
        }
    }

    public List<RatingViewModel> findByHotelId(Long id) {
        return ratingRepository.findByHotelId(id).stream()
                .map(ratingEntity -> modelMapper.map(ratingEntity, RatingViewModel.class)).collect(Collectors.toList());
    }

    public Double findAverageRating(Long id) {
        return ratingRepository.findAverageRating(id);
    }

    public List<RatingViewModel> findByUserId(Long id) {
        return ratingRepository.findByUserId(id).stream()
                .map(ratingEntity -> modelMapper.map(ratingEntity, RatingViewModel.class)).collect(Collectors.toList());
    }

    public RatingViewModel findByUserIdAndHotelId(Long hotelId) {
        return ratingRepository.findByUserIdAndHotelId(userService.getCurrentUser().getId(), hotelId)
                .map(ratingEntity -> modelMapper.map(ratingEntity, RatingViewModel.class)).orElse(new RatingViewModel().setReview("").setValue(1));
    }
}
