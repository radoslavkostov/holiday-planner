package com.example.travelagency.services;

import com.example.travelagency.entities.FavoriteEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import com.example.travelagency.repositories.FavoriteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final TravelDestinationService travelDestinationService;
    private final ModelMapper modelMapper;

    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, TravelDestinationService travelDestinationService, ModelMapper modelMapper) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.travelDestinationService = travelDestinationService;
        this.modelMapper = modelMapper;
    }


    public boolean checkIfFavorite(Long id) {
        Optional<FavoriteEntity> optional = favoriteRepository.findByTravelDestinationIdAndUserId(id, userService.getCurrentUser().getId());
        return optional.isPresent();
    }

    public void favor(Long id) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUser(userService.getCurrentUser());
        favoriteEntity.setTravelDestination(modelMapper.map(travelDestinationService.findById(id), TravelDestinationEntity.class));
        favoriteRepository.save(favoriteEntity);
    }

    public void disfavor(Long id) {
        Optional<FavoriteEntity> optional = favoriteRepository.findByTravelDestinationIdAndUserId(id, userService.getCurrentUser().getId());
        optional.ifPresent(favoriteRepository::delete);
    }

}
