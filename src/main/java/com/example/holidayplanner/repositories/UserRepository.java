package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select u from UserEntity u where u.username like %:username%")
    List<UserEntity> findAllByUsername(@Param("username") String username);


}
