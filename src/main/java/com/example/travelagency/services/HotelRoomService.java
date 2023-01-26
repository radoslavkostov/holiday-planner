package com.example.travelagency.services;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.enums.HotelRoomTypeEnum;
import com.example.travelagency.repositories.HotelRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;
    private final HotelService hotelService;

    public HotelRoomService(HotelRoomRepository hotelRoomRepository, HotelService hotelService) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelService = hotelService;
    }

    public void init(){
        if(hotelRoomRepository.count()==0){
            HotelRoomEntity hotelRoomOne = new HotelRoomEntity();
            HotelRoomEntity hotelRoomTwo = new HotelRoomEntity();
            HotelRoomEntity hotelRoomThree = new HotelRoomEntity();
            HotelRoomEntity hotelRoomFour = new HotelRoomEntity();
            HotelRoomEntity hotelRoomFive = new HotelRoomEntity();
            HotelEntity hotelOne = hotelService.findByName("Hotel One");
            HotelEntity hotelTwo = hotelService.findByName("Hotel Two");

            hotelRoomOne.setName("a");
            hotelRoomOne.setType(HotelRoomTypeEnum.SINGLE);
            hotelRoomOne.setHotel(hotelOne);
            hotelRoomTwo.setName("b");
            hotelRoomTwo.setType(HotelRoomTypeEnum.SINGLE);
            hotelRoomTwo.setHotel(hotelOne);
            hotelRoomThree.setName("c");
            hotelRoomThree.setType(HotelRoomTypeEnum.SINGLE);
            hotelRoomThree.setHotel(hotelOne);
            hotelRoomFour.setName("d");
            hotelRoomFour.setType(HotelRoomTypeEnum.SINGLE);
            hotelRoomFour.setHotel(hotelTwo);
            hotelRoomFive.setName("e");
            hotelRoomFive.setType(HotelRoomTypeEnum.SINGLE);
            hotelRoomFive.setHotel(hotelTwo);

            hotelRoomRepository.save(hotelRoomOne);
            hotelRoomRepository.save(hotelRoomTwo);
            hotelRoomRepository.save(hotelRoomThree);
            hotelRoomRepository.save(hotelRoomFour);
            hotelRoomRepository.save(hotelRoomFive);
        }
    }

    public Optional<HotelRoomEntity> checkReservations(LocalDate startDate, LocalDate endDate, HotelRoomTypeEnum type, Long hotelId){
        Optional<HotelRoomEntity> optional = hotelRoomRepository.checkReservations(startDate, endDate, type, hotelId);
        return optional;
    }

    public HotelRoomEntity findByName(String name){
        return hotelRoomRepository.findByName(name).get();
    }

    public List<HotelRoomEntity> getValidRooms(HotelRoomTypeEnum type, Long hotelId) {
        return hotelRoomRepository.findByHotelIdAndType(hotelId, type);
    }

}
