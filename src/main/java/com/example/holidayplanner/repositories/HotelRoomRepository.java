package com.example.holidayplanner.repositories;

import com.example.holidayplanner.enums.HotelRoomTypeEnum;
import com.example.holidayplanner.entities.HotelRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity, Long> {
    Optional<HotelRoomEntity> findByName(String name);

    @Query(value = "select hr from ReservationEntity r join HotelRoomEntity hr on r.hotelRoom.id=hr.id where hr.hotel.id=:hotelId and hr.type=:type and ((r.startDate<:startDate and r.endDate<:startDate) or (r.startDate>:endDate and r.endDate>:endDate))")
    Optional<HotelRoomEntity> checkReservations(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("type") HotelRoomTypeEnum type, @Param("hotelId") Long hotelId);

    List<HotelRoomEntity> findByHotelIdAndType(Long hotel_id, HotelRoomTypeEnum type);
}
