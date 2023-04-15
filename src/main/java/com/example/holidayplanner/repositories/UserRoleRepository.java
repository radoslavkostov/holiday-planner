package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.UserRoleEntity;
import com.example.holidayplanner.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUserRole(UserRoleEnum userRole);
}
