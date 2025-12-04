package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Reservation;
import model.User;
import model.RestaurantTable;
import service.ReservationService;
import service.UserService;
import service.TableService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TableService tableService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody JsonNode jsonNode) {
        try {
            System.out.println("=== JSON REÇU ===");
            System.out.println(jsonNode.toString());
            System.out.println("=================");
            
            // Extraire les IDs de l'utilisateur et de la table
            Long userId = jsonNode.get("user").get("id").asLong();
            Long tableId = jsonNode.get("table").get("id").asLong();
            
            // Récupérer l'utilisateur et la table
            Optional<User> userOpt = userService.getUserById(userId);
            Optional<RestaurantTable> tableOpt = tableService.getTableById(tableId);
            
            if (userOpt.isEmpty() || tableOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"Utilisateur ou table non trouvé\"}");
            }
            
            // Créer l'objet Reservation
            Reservation reservation = new Reservation();
            reservation.setUser(userOpt.get());
            reservation.setTable(tableOpt.get());
            reservation.setReservationDate(LocalDate.parse(jsonNode.get("reservationDate").asText()));
            reservation.setReservationTime(LocalTime.parse(jsonNode.get("reservationTime").asText()));
            reservation.setNumberOfGuests(jsonNode.get("numberOfGuests").asInt());
            reservation.setDurationHours(jsonNode.get("durationHours").asInt());
            
            // IMPORTANT: Récupérer totalPrice depuis le JSON
            if (jsonNode.has("totalPrice")) {
                reservation.setTotalPrice(jsonNode.get("totalPrice").asDouble());
                System.out.println("TotalPrice reçu: " + reservation.getTotalPrice());
            } else {
                System.out.println("ATTENTION: totalPrice non présent dans le JSON!");
            }
            
            reservation.setStatus(jsonNode.get("status").asText());
            reservation.setSpecialRequests(jsonNode.get("specialRequests").asText());
            
            Reservation savedReservation = reservationService.createReservation(reservation);
            
            System.out.println("=== RÉSERVATION CRÉÉE ===");
            System.out.println("ID: " + savedReservation.getId());
            System.out.println("TotalPrice: " + savedReservation.getTotalPrice());
            System.out.println("========================");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la création de la réservation: " + e.getMessage() + "\"}");
        }
    }
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    
    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsByUser(userId);
    }
    
    @GetMapping("/table/{tableId}")
    public List<Reservation> getReservationsByTable(@PathVariable Long tableId) {
        return reservationService.getReservationsByTable(tableId);
    }
    
    @GetMapping("/date/{date}")
    public List<Reservation> getReservationsByDate(@PathVariable String date) {
        LocalDate reservationDate = LocalDate.parse(date);
        return reservationService.getReservationsByDate(reservationDate);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
            if (updatedReservation != null) {
                return ResponseEntity.ok(updatedReservation);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la mise à jour\"}");
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            if (reservationService.cancelReservation(id)) {
                return ResponseEntity.ok().body("{\"message\": \"Réservation annulée avec succès\"}");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de l'annulation\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            if (reservationService.deleteReservation(id)) {
                return ResponseEntity.ok().body("{\"message\": \"Réservation supprimée avec succès\"}");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la suppression\"}");
        }
    }
}