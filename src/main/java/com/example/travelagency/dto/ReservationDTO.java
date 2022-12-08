package com.example.travelagency.dto;

import com.example.travelagency.enums.HotelRoomTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private int adultsCount;
    private int childrenCount;
    private HotelRoomTypeEnum type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public ReservationDTO() {
    }
}
