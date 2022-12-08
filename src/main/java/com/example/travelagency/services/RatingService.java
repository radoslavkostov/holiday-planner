package com.example.travelagency.services;

import com.example.travelagency.dto.RatingDTO;
import com.example.travelagency.entities.RatingEntity;
import com.example.travelagency.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final HotelService hotelService;
    private final UserService userService;

    public RatingService(RatingRepository ratingRepository, HotelService hotelService, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.hotelService = hotelService;
        this.userService = userService;
    }

    public void addRating(RatingDTO ratingDTO) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setReview(ratingDTO.getReview());
        ratingEntity.setHotel(hotelService.findById(ratingDTO.getHotelId()));
        ratingEntity.setUser(userService.getCurrentUser());
        ratingEntity.setValue(ratingDTO.getValue());
        ratingRepository.save(ratingEntity);
    }

    public List<RatingEntity> findByHotelId(Long id) {
        return ratingRepository.findByHotelId(id);
    }

    public Double findAverageRating(Long id) {
        return ratingRepository.findAverageRating(id);
    }

}
