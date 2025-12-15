package com.autodealer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;
    
    @Column(length = 100)
    private String email;
    
    @Column(nullable = false, length = 20)
    private String phone;
    
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(length = 50)
    private String status = "NEW";
    
    @Column(columnDefinition = "TEXT")
    private String response;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


