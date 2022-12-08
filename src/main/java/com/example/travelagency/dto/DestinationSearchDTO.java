package com.example.travelagency.dto;

import lombok.Data;

@Data
public class DestinationSearchDTO {
    private String name;
    public boolean isEmpty() {
        return (name == null || name.isEmpty());
    }

    public DestinationSearchDTO() {
    }
}
