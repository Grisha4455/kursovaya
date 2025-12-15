package com.autodealer.service;

import com.autodealer.entity.CreditOffer;
import com.autodealer.repository.CreditOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditOfferService {
    
    private final CreditOfferRepository creditOfferRepository;
    
    public List<CreditOffer> findAll() {
        return creditOfferRepository.findAll();
    }
    
    public List<CreditOffer> findActiveOffers() {
        return creditOfferRepository.findByActiveTrue();
    }
    
    public CreditOffer findById(Long id) {
        return creditOfferRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Credit offer not found"));
    }
    
    public CreditOffer save(CreditOffer creditOffer) {
        return creditOfferRepository.save(creditOffer);
    }
    
    public void delete(Long id) {
        creditOfferRepository.deleteById(id);
    }
    
    public BigDecimal calculateMonthlyPayment(BigDecimal price, BigDecimal downPayment, 
                                               BigDecimal annualRate, Integer termMonths) {
        BigDecimal loanAmount = price.subtract(downPayment);
        
        if (loanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        if (annualRate.compareTo(BigDecimal.ZERO) == 0) {
            return loanAmount.divide(new BigDecimal(termMonths), 2, RoundingMode.HALF_UP);
        }
        
        BigDecimal monthlyRate = annualRate.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP)
                                          .divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal powerTerm = onePlusRate.pow(termMonths);
        
        BigDecimal numerator = loanAmount.multiply(monthlyRate).multiply(powerTerm);
        BigDecimal denominator = powerTerm.subtract(BigDecimal.ONE);
        
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}

