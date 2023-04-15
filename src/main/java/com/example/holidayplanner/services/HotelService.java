package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelEntity;
import com.example.holidayplanner.models.service.HotelServiceModel;
import com.example.holidayplanner.models.view.HotelViewModel;
import com.example.holidayplanner.repositories.HotelRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelService(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<HotelViewModel> getHotelsByDestination(String travelDestinationName) {
        return hotelRepository.findByTravelDestination(travelDestinationName).stream()
                .map(hotelEntity -> modelMapper.map(hotelEntity, HotelViewModel.class)).collect(Collectors.toList());
    }


    @Transactional
    public List<HotelViewModel> searchHotels(HotelServiceModel hotelServiceModel) {
        List<HotelEntity> hotelEntities = hotelRepository.searchHotels(hotelServiceModel.getName().toLowerCase(), hotelServiceModel.getName().toLowerCase());
        hotelEntities.forEach(hotelEntity -> {Hibernate.initialize(hotelEntity.getRooms());
        Hibernate.initialize(hotelEntity.getRatings());
        });
        return hotelEntities.stream()
                .map(hotelEntity -> modelMapper.map(hotelEntity, HotelViewModel.class)).collect(Collectors.toList());
    }


    @Transactional
    public HotelViewModel findById(Long id) {
        HotelEntity hotelEntity = hotelRepository.findById(id).orElse(null);
        Hibernate.initialize(hotelEntity.getRooms());
        Hibernate.initialize(hotelEntity.getRatings());
        return modelMapper.map(hotelEntity, HotelViewModel.class);
    }

    @Transactional
    public List<HotelViewModel> findByUserId(Long id) {
        return hotelRepository.findByUserId(id).stream().map(hotelEntity -> modelMapper.map(hotelEntity, HotelViewModel.class)).collect(Collectors.toList());
    }
}
