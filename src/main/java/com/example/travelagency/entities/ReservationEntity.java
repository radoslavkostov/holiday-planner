package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Data
public class ReservationEntity extends BaseEntity {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private UserEntity client;

    @ManyToOne
    private UserEntity agent;

    @ManyToOne
    private HotelRoomEntity hotelRoom;

}
