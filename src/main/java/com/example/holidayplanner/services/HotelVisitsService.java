package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelEntity;
import com.example.holidayplanner.entities.HotelVisitsEntity;
import com.example.holidayplanner.repositories.HotelVisitsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelVisitsService {
    private final HotelVisitsRepository hotelVisitsRepository;
    private final HotelService hotelService;
    private final ModelMapper modelMapper;

    public HotelVisitsService(HotelVisitsRepository hotelVisitsRepository, HotelService hotelService, ModelMapper modelMapper) {
        this.hotelVisitsRepository = hotelVisitsRepository;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    public void increaseVisitsCount(Long id) {
        Optional<HotelVisitsEntity> optional = hotelVisitsRepository.findByHotelEntityId(id);
        if(optional.isPresent()){
            HotelVisitsEntity hotelVisitsEntity = optional.get();
            hotelVisitsEntity.setVisits(hotelVisitsEntity.getVisits()+1);
            hotelVisitsRepository.save(hotelVisitsEntity);
        }
        else {
            HotelEntity hotelEntity = modelMapper.map(hotelService.findById(id), HotelEntity.class);
            HotelVisitsEntity hotelVisitsEntity = new HotelVisitsEntity();
            hotelVisitsEntity.setHotelEntity(hotelEntity);
            hotelVisitsEntity.setVisits(1L);
            hotelVisitsRepository.save(hotelVisitsEntity);
        }
    }
}
