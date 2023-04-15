package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelSearchBindingModel {
    private String name;
    public boolean isEmpty() {
        return (name == null || name.isEmpty());
    }
}
