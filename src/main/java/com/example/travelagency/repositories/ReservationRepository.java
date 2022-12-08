package com.example.travelagency.repositories;

import com.example.travelagency.entities.ReservationEntity;
import com.example.travelagency.enums.HotelRoomTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = "select r from ReservationEntity r join HotelRoomEntity hr on hr.id=r.hotelRoom.id where hr.id=:roomId and r.startDate<=:endDate and r.endDate>=:startDate")
    List<ReservationEntity> reservationValidityCheck(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("roomId") Long roomId);
}
