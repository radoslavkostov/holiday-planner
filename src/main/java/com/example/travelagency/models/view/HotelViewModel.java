package com.example.travelagency.models.view;

import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.entities.RatingEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelViewModel {
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private List<HotelRoomEntity> rooms;
    private TravelDestinationEntity travelDestination;
    private List<RatingEntity> ratings;
}
