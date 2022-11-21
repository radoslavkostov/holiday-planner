package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotel_rooms")
@NoArgsConstructor
@Data
public class HotelRoomEntity extends BaseEntity {

    private String name;

    @ManyToOne
    private HotelEntity hotel;

    private String type;

}
