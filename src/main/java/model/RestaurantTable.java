package model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "restaurant_tables")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "table_number", nullable = false, unique = true)
    private String tableNumber;
    
    @Column(nullable = false)
    private Integer capacity;
    
    private String location;
    
    @Column(name = "table_type")
    private String tableType;
    
    @Column(nullable = false)
    private String status = "available";
    
    @Column(name = "price_per_hour")
    private Double pricePerHour = 0.0;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "location_description")
    private String locationDescription;
    
    // Constructors
    public RestaurantTable() {}
    
    public RestaurantTable(String tableNumber, Integer capacity, String location) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }
    
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getTableType() { return tableType; }
    public void setTableType(String tableType) { this.tableType = tableType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getLocationDescription() { return locationDescription; }
    public void setLocationDescription(String locationDescription) { this.locationDescription = locationDescription; }
}