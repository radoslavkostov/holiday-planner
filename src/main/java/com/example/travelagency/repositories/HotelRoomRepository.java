package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity, Long> {
    Optional<HotelRoomEntity> findByName(String name);
}
