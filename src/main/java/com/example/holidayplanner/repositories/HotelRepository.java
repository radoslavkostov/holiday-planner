package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @Query(value = "select h from TravelDestinationEntity d join HotelEntity h on d.id=h.travelDestination.id where lower(d.name) like %:destinationName%")
    List<HotelEntity> findByTravelDestination(@Param("destinationName") String destinationName);

    @Query(value = "select distinct h from HotelEntity h join TravelDestinationEntity d on d.id=h.travelDestination.id where lower(h.name) like %:hotelName% or lower(d.name) like :destinationName")
    List<HotelEntity> searchHotels(@Param("hotelName") String hotelName, @Param("destinationName") String destinationName);

    @Query(value = "select h from HotelEntity h join RatingEntity r on h.id=r.hotel.id join UserEntity u on r.user.id=u.id where u.id=:userId")
    List<HotelEntity> findByUserId(@Param("userId") Long userId);
}
