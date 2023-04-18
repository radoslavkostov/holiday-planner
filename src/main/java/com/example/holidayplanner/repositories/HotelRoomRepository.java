package com.example.holidayplanner.repositories;

import com.example.holidayplanner.enums.HotelRoomTypeEnum;
import com.example.holidayplanner.entities.HotelRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity, Long> {

    List<HotelRoomEntity> findByHotelIdAndType(Long hotel_id, HotelRoomTypeEnum type);
}
