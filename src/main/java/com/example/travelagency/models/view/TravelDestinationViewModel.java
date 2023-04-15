package com.example.travelagency.models.view;

import com.example.travelagency.entities.FavoriteEntity;
import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.TravelDestinationImageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TravelDestinationViewModel {
    private Long id;
    private String name;
    private String imageURL;
    private String shortDescription;
    private String longDescription;
    private List<HotelEntity> hotels;
    private List<FavoriteEntity> favorites;
    private List<TravelDestinationImageEntity> images;
}
