package com.example.travelagency.services;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.repositories.HotelRoomRepository;
import org.springframework.stereotype.Service;

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
            hotelRoomOne.setHotel(hotelOne);
            hotelRoomTwo.setName("b");
            hotelRoomTwo.setHotel(hotelOne);
            hotelRoomThree.setName("c");
            hotelRoomThree.setHotel(hotelOne);
            hotelRoomFour.setName("d");
            hotelRoomFour.setHotel(hotelTwo);
            hotelRoomFive.setName("e");
            hotelRoomFive.setHotel(hotelTwo);

            hotelRoomRepository.save(hotelRoomOne);
            hotelRoomRepository.save(hotelRoomTwo);
            hotelRoomRepository.save(hotelRoomThree);
            hotelRoomRepository.save(hotelRoomFour);
            hotelRoomRepository.save(hotelRoomFive);
        }
    }

    public HotelRoomEntity findByName(String name){
        return hotelRoomRepository.findByName(name).get();
    }
}
