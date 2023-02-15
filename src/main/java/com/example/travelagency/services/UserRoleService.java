package com.example.travelagency.services;

import com.example.travelagency.entities.UserRoleEntity;
import com.example.travelagency.enums.UserRoleEnum;
import com.example.travelagency.repositories.UserRoleRepository;
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
