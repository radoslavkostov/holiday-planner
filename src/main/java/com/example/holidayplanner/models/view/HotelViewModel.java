package com.example.holidayplanner.models.view;

import com.example.holidayplanner.entities.HotelRoomEntity;
import com.example.holidayplanner.entities.RatingEntity;
import com.example.holidayplanner.entities.TravelDestinationEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelViewModel {
    private Long id;
    private String imageURL;
    private String name;
    private String description;
    private String shortDescription;
    private List<HotelRoomEntity> rooms;
    private TravelDestinationEntity travelDestination;
    private List<RatingEntity> ratings;
}
