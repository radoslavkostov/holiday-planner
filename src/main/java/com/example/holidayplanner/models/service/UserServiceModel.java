package com.example.holidayplanner.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserServiceModel {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
}
