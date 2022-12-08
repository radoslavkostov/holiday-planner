package com.example.travelagency.services;

import com.example.travelagency.dto.DestinationSearchDTO;
import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import com.example.travelagency.repositories.TravelDestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelDestinationService {

    private final TravelDestinationRepository travelDestinationRepository;
    private final HotelService hotelService;


    public TravelDestinationService(TravelDestinationRepository travelDestinationRepository, HotelService hotelService) {
        this.travelDestinationRepository = travelDestinationRepository;
        this.hotelService = hotelService;
    }

    public void init(){
        if(travelDestinationRepository.count()==0){

            TravelDestinationEntity travelDestinationEntityOne = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityTwo = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityThree = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityFour = new TravelDestinationEntity();
            List<HotelEntity> list = hotelService.getHotels();

            travelDestinationEntityOne.setName("Destination One");
            travelDestinationEntityOne.setShortDescription("lorem ispum lorem lorem");
            travelDestinationEntityOne.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            travelDestinationEntityOne.setHotels(list);
            travelDestinationEntityTwo.setName("Destination Two");
            travelDestinationEntityTwo.setShortDescription("lorem ispum lorem lorem");
            travelDestinationEntityTwo.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            travelDestinationEntityThree.setName("Destination Three");
            travelDestinationEntityThree.setShortDescription("lorem ispum lorem lorem");
            travelDestinationEntityThree.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            travelDestinationEntityFour.setName("Destination Four");
            travelDestinationEntityFour.setShortDescription("lorem ispum lorem lorem");
            travelDestinationEntityFour.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

            travelDestinationRepository.save(travelDestinationEntityOne);
            travelDestinationRepository.save(travelDestinationEntityTwo);
            travelDestinationRepository.save(travelDestinationEntityThree);
            travelDestinationRepository.save(travelDestinationEntityFour);
        }
    }

//    public List<TravelDestinationEntity> getTopRatedDestinations() {
////        return travelDestinationRepository.findTopRatedDestinations().stream().limit(3).collect(Collectors.toList());
//    }

    public TravelDestinationEntity findById(Long id){
        return travelDestinationRepository.findById(id).orElse(null);
    }

    public List<TravelDestinationEntity> searchDestinations(DestinationSearchDTO destinationSearchDTO){
        return travelDestinationRepository.findTravelDestinationEntitiesByNameContains(destinationSearchDTO.getName());
    }

    public List<TravelDestinationEntity> findAll() {
        return travelDestinationRepository.findAll();
    }

    public TravelDestinationEntity findByName(String name){
        return travelDestinationRepository.findByName(name.toLowerCase()).orElse(null);
    }

    public List<TravelDestinationEntity> getTopFavoriteDestinations() {
        return travelDestinationRepository.findTopFavoriteDestinations().stream().limit(3).collect(Collectors.toList());
    }

}
