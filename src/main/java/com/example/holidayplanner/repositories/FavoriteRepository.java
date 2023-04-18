package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    Optional<FavoriteEntity> findByTravelDestinationIdAndUserId(Long travelDestination_id, Long user_id);

}
