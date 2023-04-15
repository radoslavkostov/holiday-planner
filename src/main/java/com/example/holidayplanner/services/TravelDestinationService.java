package com.example.holidayplanner.services;

import com.example.holidayplanner.models.service.TravelDestinationServiceModel;
import com.example.holidayplanner.models.view.TravelDestinationViewModel;
import com.example.holidayplanner.repositories.TravelDestinationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelDestinationService {

    private final TravelDestinationRepository travelDestinationRepository;
    private final ModelMapper modelMapper;


    public TravelDestinationService(TravelDestinationRepository travelDestinationRepository, ModelMapper modelMapper) {
        this.travelDestinationRepository = travelDestinationRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public TravelDestinationViewModel findById(Long id){
        return travelDestinationRepository.findById(id)
                .map(travelDestinationEntity -> modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class))
                .orElse(null);
    }

    @Transactional
    public List<TravelDestinationViewModel> searchDestinations(TravelDestinationServiceModel travelDestinationServiceModel){
        return travelDestinationRepository.findTravelDestinationEntitiesByNameContains(travelDestinationServiceModel.getName().toLowerCase())
                .stream().map(travelDestinationEntity -> modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TravelDestinationViewModel> getTopFavoriteDestinations() {
        return travelDestinationRepository.findTopFavoriteDestinations().stream()
                .map(travelDestinationEntity -> modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class))
                .limit(3).collect(Collectors.toList());
    }

    @Transactional
    public List<TravelDestinationViewModel> findByUserId(Long id) {
        return travelDestinationRepository.findByUserId(id).stream().map(travelDestinationEntity ->
                modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class)).collect(Collectors.toList());
    }
}
