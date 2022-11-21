package com.example.travelagency.services;

import com.example.travelagency.dto.ReservationBindingModel;
import com.example.travelagency.entities.ReservationEntity;
import com.example.travelagency.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

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

    public void createReservation(ReservationBindingModel reservationBindingModel) {

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setClient(userService.findByEmail(reservationBindingModel.getEmailOfClient()));
        reservationEntity.setAgent(userService.getCurrentUser());
//        reservationEntity.setHotel(hotelService.findByName(reservationBindingModel.getHotelName()));
        reservationEntity.setHotelRoom(hotelRoomService.findByName(reservationBindingModel.getRoomName()));

        reservationRepository.save(reservationEntity);
    }
}
