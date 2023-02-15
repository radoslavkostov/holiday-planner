package com.example.travelagency.repositories;

import com.example.travelagency.entities.UserRoleEntity;
import com.example.travelagency.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUserRole(UserRoleEnum userRole);
}
