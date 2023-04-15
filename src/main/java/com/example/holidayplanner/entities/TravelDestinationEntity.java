package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "travel_destinations")
@Data
public class TravelDestinationEntity extends BaseEntity {

    private String name;
    private String imageURL;
    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String longDescription;
    @OneToMany(mappedBy = "travelDestination")
    private List<HotelEntity> hotels;
    @OneToMany(mappedBy = "travelDestination")
    private List<FavoriteEntity> favorites;
    @OneToMany(mappedBy = "travelDestinationEntity")
    private List<TravelDestinationImageEntity> images;

    public TravelDestinationEntity() {
    }

    public TravelDestinationEntity(String name, String shortDescription, String longDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public void addHotel(HotelEntity hotel){
        hotels.add(hotel);
    }

    @Override
    public String toString() {
        return "TravelDestinationEntity{" +
                "name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                '}';
    }
}
