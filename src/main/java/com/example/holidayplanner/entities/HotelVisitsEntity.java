package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotel_visits")
@NoArgsConstructor
@Data
public class HotelVisitsEntity extends BaseEntity{

    @OneToOne
    private HotelEntity hotelEntity;

    private Long visits;

}
