package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.HotelVisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelVisitsRepository extends JpaRepository<HotelVisitsEntity, Long> {
    Optional<HotelVisitsEntity> findByHotelEntityId(Long hotelEntity_id);
}
