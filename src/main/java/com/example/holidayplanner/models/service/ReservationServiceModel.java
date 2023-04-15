package com.example.holidayplanner.models.service;

import com.example.holidayplanner.enums.HotelRoomTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationServiceModel {
    private int adultsCount;
    private int childrenCount;
    private HotelRoomTypeEnum type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
