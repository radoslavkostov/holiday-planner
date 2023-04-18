package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchBindingModel {
    private String username;
    public boolean isEmpty() {
        return (username == null || username.isEmpty());
    }
}
