package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RatingAddBindingModel {

    @Size(min = 20, max = 5000, message = "Review size should be between 20 and 5000 characters.")
    private String review;
    @Min(1)
    @Max(5)
    private int value;
}
