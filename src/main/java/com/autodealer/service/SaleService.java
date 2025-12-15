package com.autodealer.service;

import com.autodealer.entity.Sale;
import com.autodealer.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleService {
    
    private final SaleRepository saleRepository;
    
    public List<Sale> findAll() {
        return saleRepository.findAllOrderByDateDesc();
    }
    
    public Sale findById(Long id) {
        return saleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Продажи не найдено"));
    }
    
    public List<Sale> findByCustomerId(Long customerId) {
        return saleRepository.findByCustomerId(customerId);
    }
    
    public List<Sale> findByEmployeeId(Long employeeId) {
        return saleRepository.findByEmployeeId(employeeId);
    }
    
    public List<Sale> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findBySaleDateBetween(start, end);
    }
    
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }
    
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }
    
    // ���������
    public List<Object[]> getPopularModels() {
        return saleRepository.findPopularModels();
    }
    
    public List<Object[]> getSalesByEmployee() {
        return saleRepository.findSalesByEmployee();
    }
}

