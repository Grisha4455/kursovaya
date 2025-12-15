package com.autodealer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String brand;
    
    @Column(length = 50)
    private String color;
    
    @Column(length = 50)
    private String upholstery;
    
    @Column(name = "engine_type", length = 50)
    private String engineType;
    
    @Column(name = "engine_power")
    private Integer enginePower;
    
    @Column(length = 50)
    private String transmission;
    
    private Integer doors;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToMany
    @JoinTable(
        name = "car_model_suppliers",
        joinColumns = @JoinColumn(name = "car_model_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    @Builder.Default
    private Set<Supplier> suppliers = new HashSet<>();
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


