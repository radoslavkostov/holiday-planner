package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
public class FavoriteEntity extends BaseEntity{

    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private TravelDestinationEntity travelDestination;

    @Override
    public String toString() {
        return "FavoriteEntity{" +
                '}';
    }
}
