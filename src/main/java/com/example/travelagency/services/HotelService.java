package com.example.travelagency.services;

import com.example.travelagency.dto.DestinationSearchDTO;
import com.example.travelagency.dto.HotelSearchDTO;
import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import com.example.travelagency.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return hotelRepository.findByName(name).orElse(null);
    }

    public List<HotelEntity> getHotels(){
        return hotelRepository.findAll();
    }

    public List<HotelEntity> getHotelsByDestination(TravelDestinationEntity travelDestinationEntity) {
        return hotelRepository.findByTravelDestination(travelDestinationEntity.getName());
    }

    public List<HotelEntity> searchHotels(HotelSearchDTO hotelSearchDTO) {
        return hotelRepository.searchHotels(hotelSearchDTO.getName().toLowerCase());
    }

    public HotelEntity findById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }
}
