package com.example.travelagency.repositories;

import com.example.travelagency.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
//    Optional<FavoriteEntity> checkIfFavorite(@Param(value = "userId") Long userId, @Param(value = "destinationId") Long destinationId);

    Optional<FavoriteEntity> findByTravelDestinationIdAndUserId(Long travelDestination_id, Long user_id);
}
