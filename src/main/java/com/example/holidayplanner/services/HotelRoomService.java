package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelRoomEntity;
import com.example.holidayplanner.enums.HotelRoomTypeEnum;
import com.example.holidayplanner.repositories.HotelRoomRepository;
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
