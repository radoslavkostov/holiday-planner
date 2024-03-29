package com.example.holidayplanner.entities;

import com.example.holidayplanner.enums.HotelRoomTypeEnum;
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

    @Override
    public String toString() {
        return "HotelRoomEntity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
