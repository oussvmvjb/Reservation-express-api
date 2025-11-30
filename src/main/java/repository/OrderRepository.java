package repository;

import model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByUserId(Long userId);
    
    List<Order> findByReservationId(Long reservationId);
    
    List<Order> findByStatus(String status);
    
    // Méthode optimisée avec JOIN FETCH
    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.reservation JOIN FETCH o.table WHERE o.user.id = :userId")
    List<Order> findByUserIdWithDetails(@Param("userId") Long userId);
    
    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.reservation JOIN FETCH o.table")
    List<Order> findAllWithDetails();
}