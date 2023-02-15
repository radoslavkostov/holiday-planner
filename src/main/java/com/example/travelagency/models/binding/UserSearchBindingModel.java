package com.example.travelagency.models.binding;

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
