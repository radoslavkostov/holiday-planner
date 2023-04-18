package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.TravelDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelDestinationRepository extends JpaRepository<TravelDestinationEntity, Long> {

    List<TravelDestinationEntity> findTravelDestinationEntitiesByNameContains(String name);

    @Query(value = "select d from TravelDestinationEntity d join FavoriteEntity f on d.id=f.travelDestination.id group by d.id order by count(f.id) desc")
    List<TravelDestinationEntity> findTopFavoriteDestinations();

    @Query(value = "select d from TravelDestinationEntity d join FavoriteEntity f on d.id=f.travelDestination.id join UserEntity u on f.user.id=u.id where u.id = :userId order by d.name")
    List<TravelDestinationEntity> findByUserId(@Param("userId") Long userId);

}
