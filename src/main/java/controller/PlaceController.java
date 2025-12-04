package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import model.Place;
import service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {
    
    @Autowired
    private PlaceService placeService;
    
    // Get all places
    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }
    
    // Get place by ID
    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id) {
        try {
            Place place = placeService.getPlaceById(id);
            return ResponseEntity.ok(place);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Get all unique cities (categories)
    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(placeService.getAllCities());
    }
    
    // Note: Changed endpoint name from "/categories" to "/cities"
    
    // Get places by city
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Place>> getPlacesByCity(@PathVariable String city) {
        return ResponseEntity.ok(placeService.getPlacesByCity(city));
    }
    
    // Search places
    @GetMapping("/search")
    public ResponseEntity<List<Place>> searchPlaces(@RequestParam String q) {
        return ResponseEntity.ok(placeService.searchPlaces(q));
    }
    
    // Create new place
    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        return ResponseEntity.ok(placeService.createPlace(place));
    }
    
    // Update place
    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody Place placeDetails) {
        try {
            Place updatedPlace = placeService.updatePlace(id, placeDetails);
            return ResponseEntity.ok(updatedPlace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Delete place
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        try {
            placeService.deletePlace(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}