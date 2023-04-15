package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "travel_destinations_images")
@Data
public class TravelDestinationImageEntity extends BaseEntity {

    private String imageURL;

    @ManyToOne
    private TravelDestinationEntity travelDestinationEntity;

}
