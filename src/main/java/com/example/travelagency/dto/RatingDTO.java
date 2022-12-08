package com.example.travelagency.dto;

import lombok.Data;

@Data
public class RatingDTO {
    private String review;
    private int value;
    private Long hotelId;

    public RatingDTO() {
    }
}
