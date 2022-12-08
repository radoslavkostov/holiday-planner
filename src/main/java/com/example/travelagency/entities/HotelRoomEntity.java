package com.example.travelagency.entities;

import com.example.travelagency.enums.HotelRoomTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotel_rooms")
@NoArgsConstructor
@Data
public class HotelRoomEntity extends BaseEntity {

    private String name;

    @ManyToOne
    private HotelEntity hotel;

    @OneToMany(mappedBy = "hotelRoom")
    private List<ReservationEntity> reservations;

    @Enumerated(EnumType.STRING)
    private HotelRoomTypeEnum type;

}
