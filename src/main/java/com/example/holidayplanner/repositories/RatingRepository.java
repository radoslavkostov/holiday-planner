package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    @Query(value = "select r from RatingEntity r join HotelEntity h on r.hotel.id = h.id where h.id=:hotelId order by h.id desc")
    List<RatingEntity> findByHotelId(@Param("hotelId") Long hotelId);

    @Query(value = "select avg(r.value) from RatingEntity r join HotelEntity h on h.id=r.hotel.id where h.id=:hotelId group by h.id")
    Double findAverageRating(@Param("hotelId") Long hotelId);

    Optional<RatingEntity> findByUserIdAndHotelId(Long user_id, Long hotel_id);
}
