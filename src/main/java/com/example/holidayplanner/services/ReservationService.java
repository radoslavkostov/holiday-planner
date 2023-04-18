package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelRoomEntity;
import com.example.holidayplanner.entities.ReservationEntity;
import com.example.holidayplanner.models.service.ReservationServiceModel;
import com.example.holidayplanner.models.view.ReservationViewModel;
import com.example.holidayplanner.repositories.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final HotelRoomService hotelRoomService;
    private final ModelMapper modelMapper;

    public ReservationService(ReservationRepository reservationRepository, UserService userService, HotelRoomService hotelRoomService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.hotelRoomService = hotelRoomService;
        this.modelMapper = modelMapper;
    }

    public boolean chooseRooms(ReservationServiceModel reservationServiceModel, Long hotelId) {
        List<HotelRoomEntity> roomList = hotelRoomService.getValidRooms(reservationServiceModel.getType(), hotelId);
        for (HotelRoomEntity hotelRoomEntity : roomList) {
            List<ReservationEntity> list = reservationRepository.findIntersectingReservations(reservationServiceModel.getStartDate(), reservationServiceModel.getEndDate(), hotelRoomEntity.getId());
            if(list.isEmpty()){
                ReservationEntity reservation = modelMapper.map(reservationServiceModel, ReservationEntity.class);
                reservation.setHotelRoom(hotelRoomEntity);
                reservation.setUser(userService.getCurrentUser());
                reservationRepository.save(reservation);
                return true;
            }
        }

        return false;
    }

    public List<ReservationViewModel> findActiveByUserId(Long id) {
        return reservationRepository.findActiveByUserId(id, LocalDate.now()).stream()
                .map(reservationEntity -> modelMapper.map(reservationEntity, ReservationViewModel.class))
                .collect(Collectors.toList());
    }

    public List<ReservationViewModel> findExpiredByUserId(Long id) {
        return reservationRepository.findExpiredByUserId(id, LocalDate.now()).stream()
                .map(reservationEntity -> modelMapper.map(reservationEntity, ReservationViewModel.class))
                .collect(Collectors.toList());
    }

}
