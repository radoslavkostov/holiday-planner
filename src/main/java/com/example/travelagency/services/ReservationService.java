package com.example.travelagency.services;

import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.entities.ReservationEntity;
import com.example.travelagency.models.service.ReservationServiceModel;
import com.example.travelagency.models.view.ReservationViewModel;
import com.example.travelagency.repositories.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;
    private final ModelMapper modelMapper;

    public ReservationService(ReservationRepository reservationRepository, UserService userService, HotelService hotelService, HotelRoomService hotelRoomService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.hotelService = hotelService;
        this.hotelRoomService = hotelRoomService;
        this.modelMapper = modelMapper;
    }

    public boolean chooseRooms(ReservationServiceModel reservationServiceModel, Long hotelId) {
        /*repository query to check if reservation can be created and if there is, create one, if its not possible
        then return false and we redirect to the same page with the alrdy inserted values in inputs with RedirectAttributes
        and tell user there isnt an available room with error message/ if its successful we redirect to w/e mby success
        page or to profile of user where he can see all his reservations*/
        List<HotelRoomEntity> roomList = hotelRoomService.getValidRooms(reservationServiceModel.getType(), hotelId);
        for (HotelRoomEntity hotelRoomEntity : roomList) {
            List<ReservationEntity> list = reservationRepository.reservationValidityCheck(reservationServiceModel.getStartDate(), reservationServiceModel.getEndDate(), hotelRoomEntity.getId());
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

    public List<ReservationViewModel> findByUserId(Long id) {
        return reservationRepository.findByUserId(id).stream()
                .map(reservationEntity -> modelMapper.map(reservationEntity, ReservationViewModel.class))
                .collect(Collectors.toList());
    }

    public ReservationViewModel findById(Long id){
        return reservationRepository.findById(id).map(reservationEntity -> modelMapper.map(reservationEntity, ReservationViewModel.class))
                .orElse(null);
    }

    public boolean isOwner(String username, Long reservationId){
        return reservationRepository.findById(reservationId).filter(r -> r.getUser().getEmail().equals(username)).isPresent();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
