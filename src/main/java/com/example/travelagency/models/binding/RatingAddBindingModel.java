package com.example.travelagency.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RatingAddBindingModel {

    @Size(min = 20, max = 5000, message = "Review content should be between 20 and 2000 symbols.")
    private String review;
    @Min(1)
    @Max(5)
    private int value;
}
