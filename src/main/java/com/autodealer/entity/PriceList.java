package com.autodealer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;
    
    @Column(name = "production_year", nullable = false)
    private Integer productionYear;
    
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;
    
    @Column(name = "pre_sale_preparation", precision = 12, scale = 2)
    private BigDecimal preSalePreparation = BigDecimal.ZERO;
    
    @Column(name = "transport_costs", precision = 12, scale = 2)
    private BigDecimal transportCosts = BigDecimal.ZERO;
    
    @Column(name = "final_price", nullable = false, precision = 12, scale = 2, insertable = false, updatable = false)
    private BigDecimal finalPrice;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

