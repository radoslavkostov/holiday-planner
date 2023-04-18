package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserEditBindingModel {
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters.")
    private String username;
    public boolean isEmpty() {
        return (username == null || username.isEmpty());
    }
}
