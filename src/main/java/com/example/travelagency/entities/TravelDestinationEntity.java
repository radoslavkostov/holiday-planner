package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "travel_destinations")
@NoArgsConstructor
@Data
public class TravelDestinationEntity extends BaseEntity {

    private String name;
    @OneToMany
    private List<HotelEntity> hotels;
}
