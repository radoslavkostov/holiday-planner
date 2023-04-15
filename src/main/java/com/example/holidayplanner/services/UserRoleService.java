package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.UserRoleEntity;
import com.example.holidayplanner.enums.UserRoleEnum;
import com.example.holidayplanner.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public UserRoleEntity findByUserRole(UserRoleEnum userRoleEnum) {
        return userRoleRepository.findByUserRole(userRoleEnum).orElse(null);
    }
}
