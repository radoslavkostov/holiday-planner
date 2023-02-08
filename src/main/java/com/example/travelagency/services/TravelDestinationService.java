package com.example.travelagency.services;

import com.example.travelagency.entities.HotelEntity;
import com.example.travelagency.entities.TravelDestinationEntity;
import com.example.travelagency.models.service.TravelDestinationServiceModel;
import com.example.travelagency.models.view.TravelDestinationViewModel;
import com.example.travelagency.repositories.TravelDestinationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelDestinationService {

    private final TravelDestinationRepository travelDestinationRepository;
    private final HotelService hotelService;
    private final ModelMapper modelMapper;


    public TravelDestinationService(TravelDestinationRepository travelDestinationRepository, HotelService hotelService, ModelMapper modelMapper) {
        this.travelDestinationRepository = travelDestinationRepository;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    public void init(){
        if(travelDestinationRepository.count()==0){

            TravelDestinationEntity travelDestinationEntityOne = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityTwo = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityThree = new TravelDestinationEntity();
            TravelDestinationEntity travelDestinationEntityFour = new TravelDestinationEntity();
            TravelDestinationEntity destinationOne = new TravelDestinationEntity("Sofia", "Sofia, Bulgaria's vibrant capital, offers rich history, culture and entertainment for all types of travelers.", "Sofia, the charming capital of Bulgaria, is a city with a rich history that spans thousands of years. From its ancient ruins to its modern architecture, Sofia has something to offer every type of traveler. Visitors can explore the city's many museums, parks, and landmarks, including the impressive Alexander Nevsky Cathedral, the stunning National Palace of Culture, and the iconic Vitosha Mountain. With its delicious cuisine, friendly locals, and vibrant nightlife, Sofia is a must-visit destination for anyone traveling to Eastern Europe.");
            travelDestinationRepository.save(destinationOne);
            TravelDestinationEntity destinationTwo = new TravelDestinationEntity("Rome", "Rome, the Eternal City, offers ancient history, art and iconic cuisine.", "Rome, the Eternal City, is one of the most famous tourist destinations in the world. With its stunning architecture, rich history, world-class museums, and delicious cuisine, it's easy to see why millions of visitors flock to Rome every year. From the Colosseum to the Vatican, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationTwo);
            TravelDestinationEntity destinationThree = new TravelDestinationEntity("Bali", "Bali, the Island of the Gods, offers breathtaking beaches, temples and culture.", "Bali, the Island of the Gods, is one of the most popular tourist destinations in Indonesia. With its stunning beaches, lush jungles, and rich cultural heritage, it's easy to see why millions of visitors flock to Bali every year. From the iconic rice paddies to the beautiful temples, there's never a shortage of things to see and do in this magnificent island.");
            travelDestinationRepository.save(destinationThree);
            TravelDestinationEntity destinationFour = new TravelDestinationEntity("Barcelona", "Barcelona, the cosmopolitan city, offers modernist architecture, beaches and culture.", "Barcelona, the cosmopolitan city, is one of the most famous tourist destinations in Spain. With its stunning modernist architecture, rich cultural heritage, world-class museums, and beautiful beaches, it's easy to see why millions of visitors flock to Barcelona every year. From the iconic Sagrada Familia to Park Güell, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationFour);
            TravelDestinationEntity destinationFive = new TravelDestinationEntity("Berlin", "Berlin, the eclectic city, offers a unique mix of history, culture and nightlife.", "Berlin, the eclectic city, is a hub of creativity and diversity, attracting visitors from all over the world. With its rich history, cutting-edge cultural scene, and legendary nightlife, it's easy to see why Berlin is a must-visit destination. From the iconic Brandenburg Gate to the vibrant street art, there's never a shortage of things to see and do in this vibrant city.");
            travelDestinationRepository.save(destinationFive);
            TravelDestinationEntity destinationSix = new TravelDestinationEntity("Sydney", "Sydney, the sun-soaked city, offers iconic beaches, attractions and cuisine.", "Sydney, the sun-soaked city, is Australia's premier destination for both locals and tourists alike. With its stunning beaches, world-famous attractions, and diverse culinary scene, it's easy to see why millions of visitors flock to Sydney every year. From the iconic Sydney Opera House to the beautiful Bondi Beach, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationSix);
            TravelDestinationEntity destinationSeven = new TravelDestinationEntity("Cape Town", "Cape Town, the Mother City, offers breathtaking scenery, history and culture.", "Cape Town, the Mother City, is a destination like no other, offering visitors breathtaking scenery, rich history, and a vibrant cultural scene. With its stunning natural beauty, world-class attractions, and delicious cuisine, it's easy to see why Cape Town is a must-visit destination. From the iconic Table Mountain to the stunning beaches, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationSeven);
            TravelDestinationEntity destinationEight = new TravelDestinationEntity("Tokyo", "Tokyo, the bustling metropolis, offers cutting-edge technology, culture and cuisine.", "Tokyo, the bustling metropolis of Japan, is a city like no other, offering visitors a unique blend of cutting-edge technology, rich cultural heritage, and delicious cuisine. With its vibrant energy, world-famous attractions, and diverse neighborhoods, it's easy to see why millions of visitors flock to Tokyo every year. From the iconic Shibuya Crossing to the beautiful temples and shrines, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationEight);
            TravelDestinationEntity destinationNine = new TravelDestinationEntity("Buenos Aires", "Buenos Aires, the Paris of South America, offers tango, steak and history.", "Buenos Aires, the Paris of South America located in Argentina, is a city known for its passionate tango dancing, delicious steak, and rich history. With its vibrant energy, stunning architecture, and rich cultural scene, it's easy to see why millions of visitors flock to Buenos Aires every year. From the iconic San Telmo neighborhood to the stunning Teatro Colon, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationNine);
            TravelDestinationEntity destinationTen = new TravelDestinationEntity("Cairo", "Cairo, the city of the Pharaohs, offers ancient history, culture and hospitality.", "Cairo, the city of the Pharaohs located in Egypt, is a destination like no other, offering visitors a unique blend of ancient history, rich cultural heritage, and warm hospitality. With its stunning pyramids, world-class museums, and delicious cuisine, it's easy to see why millions of visitors flock to Cairo every year. From the iconic Sphinx to the beautiful Al-Azhar Mosque, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationTen);
            TravelDestinationEntity destinationEleven = new TravelDestinationEntity("Amsterdam", "Amsterdam, the city of canals, offers art, culture and history.", "Amsterdam, the city of canals located in the Netherlands, is a destination like no other, offering visitors a unique blend of stunning art, rich cultural heritage, and fascinating history. With its beautiful canals, world-famous museums, and legendary nightlife, it's easy to see why millions of visitors flock to Amsterdam every year. From the iconic Anne Frank House to the vibrant Red Light District, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationEleven);
            TravelDestinationEntity destinationTwelve = new TravelDestinationEntity("Mexico City", "Mexico City, the heart of Mexico, offers culture, history and cuisine.", "Mexico City, the heart of Mexico, is a city like no other, offering visitors a unique blend of rich cultural heritage, fascinating history, and delicious cuisine. With its vibrant energy, world-class museums, and beautiful architecture, it's easy to see why millions of visitors flock to Mexico City every year. From the iconic Teotihuacan Pyramids to the stunning Chapultepec Park, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationTwelve);
            TravelDestinationEntity destinationThirteen = new TravelDestinationEntity("New York City", "New York City, the city that never sleeps, offers iconic landmarks, world-class museums and a diverse culture.", "New York City, the city that never sleeps located in the United States, is a destination like no other, offering visitors a unique blend of iconic landmarks, world-class museums, and diverse culture. With its bustling energy, world-famous attractions, and delicious cuisine, it's easy to see why millions of visitors flock to New York City every year. From the iconic Central Park to the stunning Metropolitan Museum of Art, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationThirteen);
            TravelDestinationEntity destinationFourteen = new TravelDestinationEntity("Hong Kong", "Hong Kong, the city of lights, offers towering skyscrapers, delicious cuisine and a rich culture.", "Hong Kong, the city of lights located in China, is a destination like no other, offering visitors a unique blend of towering skyscrapers, delicious cuisine, and rich culture. With its vibrant energy, world-famous attractions, and stunning harbor, it's easy to see why millions of visitors flock to Hong Kong every year. From the iconic Victoria Peak to the beautiful Temple Street Night Market, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationFourteen);
            TravelDestinationEntity destinationFifteen = new TravelDestinationEntity("Bangkok", "Bangkok, the city of temples, offers stunning architecture, delicious street food and a rich culture.", "Bangkok, the city of temples located in Thailand, is a destination like no other, offering visitors a unique blend of stunning architecture, delicious street food, and rich culture. With its vibrant energy, world-famous landmarks, and stunning temples, it's easy to see why millions of visitors flock to Bangkok every year. From the iconic Wat Arun to the beautiful Chatuchak Weekend Market, there's never a shortage of things to see and do in this magnificent city.");
            travelDestinationRepository.save(destinationFifteen);

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

    public List<TravelDestinationViewModel> findAll() {
        return travelDestinationRepository.findAll().stream()
                .map(travelDestinationEntity -> modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class))
                .collect(Collectors.toList());
    }

    public TravelDestinationViewModel findByName(String name){
        return travelDestinationRepository.findByName(name.toLowerCase())
                .map(travelDestinationEntity -> modelMapper.map(travelDestinationEntity, TravelDestinationViewModel.class))
                .orElse(null);
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
