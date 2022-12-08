package com.example.travelagency.services;

import com.example.travelagency.dto.ReservationDTO;
import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.entities.ReservationEntity;
import com.example.travelagency.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;

    public ReservationService(ReservationRepository reservationRepository, UserService userService, HotelService hotelService, HotelRoomService hotelRoomService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
    }

    public boolean chooseRooms(ReservationDTO reservationDTO, Long hotelId) {
        /*repository query to check if reservation can be created and if there is, create one, if its not possible
        then return false and we redirect to the same page with the alrdy inserted values in inputs with RedirectAttributes
        and tell user there isnt an available room with error message/ if its successful we redirect to w/e mby success
        page or to profile of user where he can see all his reservations*/
        List<HotelRoomEntity> roomList = hotelRoomService.getValidRooms(reservationDTO.getType(), hotelId);
        for (HotelRoomEntity hotelRoomEntity : roomList) {
            List<ReservationEntity> list = reservationRepository.reservationValidityCheck(reservationDTO.getStartDate(), reservationDTO.getEndDate(), hotelRoomEntity.getId());
            if(list.isEmpty()){
                ReservationEntity reservation = new ReservationEntity();
                reservation.setHotelRoom(hotelRoomEntity);
                reservation.setAdultsCount(reservationDTO.getAdultsCount());
                reservation.setChildrenCount(reservationDTO.getChildrenCount());
                reservation.setStartDate(reservationDTO.getStartDate());
                reservation.setEndDate(reservationDTO.getEndDate());
                reservation.setUser(userService.getCurrentUser());
                reservationRepository.save(reservation);
                return true;
            }
        }

        return false;
    }
}
