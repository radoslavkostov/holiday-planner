package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, Long> {
    Optional<ForumEntity> getForumEntityById(Long id);
}
