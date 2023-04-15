package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotels")
@NoArgsConstructor
@Data
public class HotelEntity extends BaseEntity {

    private String name;

    private String imageURL;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String shortDescription;

    @OneToMany(mappedBy = "hotel")
    private List<HotelRoomEntity> rooms;

    @ManyToOne
    private TravelDestinationEntity travelDestination;

    @OneToMany(mappedBy = "hotel")
    private List<RatingEntity> ratings;

    @Override
    public String toString() {
        return "HotelEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                '}';
    }
}
