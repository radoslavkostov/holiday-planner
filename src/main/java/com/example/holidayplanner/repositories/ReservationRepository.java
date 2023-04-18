package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = "select r from ReservationEntity r join HotelRoomEntity hr on hr.id=r.hotelRoom.id where hr.id=:roomId and r.startDate<=:endDate and r.endDate>=:startDate")
    List<ReservationEntity> findIntersectingReservations(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("roomId") Long roomId);

    @Query(value = "select r from ReservationEntity r join UserEntity u on r.user.id=u.id where r.endDate<:currentDate and u.id=:userId order by r.startDate")
    List<ReservationEntity> findExpiredByUserId(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);

    @Query(value = "select r from ReservationEntity r join UserEntity u on r.user.id=u.id where r.endDate>=:currentDate and u.id=:userId order by r.startDate")
    List<ReservationEntity> findActiveByUserId(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);

}
