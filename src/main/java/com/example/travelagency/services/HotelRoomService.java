package com.example.travelagency.services;

import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.enums.HotelRoomTypeEnum;
import com.example.travelagency.repositories.HotelRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;

    public HotelRoomService(HotelRoomRepository hotelRoomRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
    }

    public List<HotelRoomEntity> getValidRooms(HotelRoomTypeEnum type, Long hotelId) {
        return hotelRoomRepository.findByHotelIdAndType(hotelId, type);
    }

}
