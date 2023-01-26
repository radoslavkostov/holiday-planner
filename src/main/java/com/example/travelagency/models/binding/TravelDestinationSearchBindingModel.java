package com.example.travelagency.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TravelDestinationSearchBindingModel {
    private String name;
    public boolean isEmpty() {
        return (name == null || name.isEmpty());
    }
}
