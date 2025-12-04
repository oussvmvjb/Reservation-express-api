package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.Place;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    List<Place> findByCity(String city);
    
    List<Place> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT DISTINCT p.city FROM Place p")
    List<String> findDistinctCities();
    
    @Query("SELECT p FROM Place p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Place> searchPlaces(String query);
}