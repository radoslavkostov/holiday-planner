package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.DestinationVisitsEntity;
import com.example.holidayplanner.repositories.DestinationVisitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationVisitsService {

    private final DestinationVisitsRepository destinationVisitsRepository;

    public DestinationVisitsService(DestinationVisitsRepository destinationVisitsRepository) {
        this.destinationVisitsRepository = destinationVisitsRepository;
    }

    public void increaseVisitsCount(Long id) {
        Optional<DestinationVisitsEntity> optional = destinationVisitsRepository.findByTravelDestinationEntityId(id);
        if(optional.isPresent()){
            DestinationVisitsEntity destinationVisitsEntity = optional.get();
            destinationVisitsEntity.setVisits(destinationVisitsEntity.getVisits()+1);
            destinationVisitsRepository.save(destinationVisitsEntity);
        }
    }

    public List<DestinationVisitsEntity> findAll(){
        return destinationVisitsRepository.findAll();
    }
}
