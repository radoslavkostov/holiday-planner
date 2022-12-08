package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "travel_destinations")
@Data
public class TravelDestinationEntity extends BaseEntity {

    private String name;
    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String longDescription;
    @OneToMany(mappedBy = "travelDestination")
    private List<HotelEntity> hotels;
    @OneToMany(mappedBy = "travelDestination")
    private List<FavoriteEntity> favorites;

    public TravelDestinationEntity() {
    }

    public void addHotel(HotelEntity hotel){
        hotels.add(hotel);
    }
}
