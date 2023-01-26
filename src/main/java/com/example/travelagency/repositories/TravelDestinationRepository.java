package com.example.travelagency.repositories;

import com.example.travelagency.entities.TravelDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelDestinationRepository extends JpaRepository<TravelDestinationEntity, Long> {
//    @Query(value = "select d from TravelDestinationEntity d join RatingEntity r on d = r.travelDestination group by d order by sum(r.value)/count(r.value) desc")
//    List<TravelDestinationEntity> findTopRatedDestinations();

    List<TravelDestinationEntity> findTravelDestinationEntitiesByNameContains(String name);

    @Query(value = "select d from TravelDestinationEntity d where lower(d.name) like %:destinationName%")
    Optional<TravelDestinationEntity> findByName(@Param("destinationName") String destinationName);

    @Query(value = "select d from TravelDestinationEntity d join FavoriteEntity f on d.id=f.travelDestination.id group by d.id order by count(f.id) desc")
    List<TravelDestinationEntity> findTopFavoriteDestinations();

    @Query(value = "select d from TravelDestinationEntity d join FavoriteEntity f on d.id=f.travelDestination.id join UserEntity u on f.user.id=u.id where u.id = :userId")
    List<TravelDestinationEntity> findByUserId(@Param("userId") Long userId);

}
