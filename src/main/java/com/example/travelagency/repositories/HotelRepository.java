package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    Optional<HotelEntity> findByName(String name);

    @Query(value = "select h from TravelDestinationEntity d join HotelEntity h on d.id=h.travelDestination.id where d.name like %:destinationName%")
    List<HotelEntity> findByTravelDestination(@Param("destinationName") String destinationName);

    @Query(value = "select h from HotelEntity h where lower(h.name) like %:hotelName%")
    List<HotelEntity> searchHotels(@Param("hotelName") String hotelName);
}
