package com.example.holidayplanner.models.view;

import com.example.holidayplanner.entities.HotelEntity;
import com.example.holidayplanner.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingViewModel {
    private Long id;
    private Integer value;
    private String review;
    private UserEntity user;
    private HotelEntity hotel;

    public RatingViewModel setValue(Integer value) {
        this.value = value;
        return this;
    }

    public RatingViewModel setReview(String review) {
        this.review = review;
        return this;
    }
}
