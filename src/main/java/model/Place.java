package model;

import jakarta.persistence.*;

@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String city;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    
    @Column(nullable = false)
    private Double price;
    
    // Constructors
    public Place() {}
    
    public Place(String name, String city, String description, String imageUrl, Double price) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}