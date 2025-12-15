package com.autodealer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOffer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "min_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal minRate;
    
    @Column(name = "max_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxRate;
    
    @Column(name = "min_term_months", nullable = false)
    private Integer minTermMonths;
    
    @Column(name = "max_term_months", nullable = false)
    private Integer maxTermMonths;
    
    @Column(name = "min_down_payment_percent", nullable = false, precision = 5, scale = 2)
    private BigDecimal minDownPaymentPercent;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

