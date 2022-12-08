package com.example.travelagency.dto;

import lombok.Data;

@Data
public class HotelSearchDTO {
    private String name;
    public boolean isEmpty() {
        return (name == null || name.isEmpty());
    }

    public HotelSearchDTO() {
    }
}
