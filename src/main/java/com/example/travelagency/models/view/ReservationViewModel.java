package com.example.travelagency.models.view;

import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationViewModel {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int adultsCount;

    private int childrenCount;

    private UserEntity user;

    private HotelRoomEntity hotelRoom;
}
