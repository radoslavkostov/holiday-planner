package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Data
public class ReservationEntity extends BaseEntity {

    private LocalDate startDate;

    private LocalDate endDate;

    private int adultsCount;

    private int childrenCount;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private HotelRoomEntity hotelRoom;

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", adultsCount=" + adultsCount +
                ", childrenCount=" + childrenCount +
                '}';
    }
}
