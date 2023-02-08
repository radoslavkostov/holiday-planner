package com.example.travelagency.models.binding;

import com.example.travelagency.enums.HotelRoomTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationAddBindingModel {

    @Min(value = 1, message = "Adult count cannot be zero.")
    private int adultsCount;

    @NotNull
    private int childrenCount;

    @NotNull(message = "Choose room type.")
    private HotelRoomTypeEnum type;

    @NotNull(message = "No date chosen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Date cannot be in the past.")
    private LocalDate startDate;

    @NotNull(message = "No date chosen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Date cannot be in the past.")
    private LocalDate endDate;

}
