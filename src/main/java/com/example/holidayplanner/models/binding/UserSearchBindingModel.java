package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchBindingModel {
    private String email;
    public boolean isEmpty() {
        return (email == null || email.isEmpty());
    }
}
