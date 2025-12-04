package service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Place;
import repository.PlaceRepository;

import java.util.List;

@Service
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepository;
    
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
    
    public Place getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));
    }
    
    public Place createPlace(Place place) {
        return placeRepository.save(place);
    }
    
    public Place updatePlace(Long id, Place placeDetails) {
        Place place = getPlaceById(id);
        
        place.setName(placeDetails.getName());
        place.setCity(placeDetails.getCity());
        place.setDescription(placeDetails.getDescription());
        place.setImageUrl(placeDetails.getImageUrl());
        place.setPrice(placeDetails.getPrice());
        
        return placeRepository.save(place);
    }
    
    public void deletePlace(Long id) {
        Place place = getPlaceById(id);
        placeRepository.delete(place);
    }
    
    public List<Place> getPlacesByCity(String city) {
        return placeRepository.findByCity(city);
    }
    
    public List<Place> searchPlaces(String query) {
        return placeRepository.searchPlaces(query);
    }
    
    public List<String> getAllCities() {
        return placeRepository.findDistinctCities();
    }
}