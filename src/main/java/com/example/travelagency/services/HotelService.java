package com.example.travelagency.services;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.repositories.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public void init(){
        if(hotelRepository.count()==0){
            HotelEntity hotelOne = new HotelEntity();
            hotelOne.setName("Hotel One");
            HotelEntity hotelTwo = new HotelEntity();
            hotelTwo.setName("Hotel Two");

            hotelRepository.save(hotelOne);
            hotelRepository.save(hotelTwo);
        }
    }

    public HotelEntity findByName(String name){
        return hotelRepository.findByName(name).get();
    }
}
