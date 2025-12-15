package com.autodealer.service;

import com.autodealer.entity.CustomerRequest;
import com.autodealer.repository.CustomerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerRequestService {
    
    private final CustomerRequestRepository customerRequestRepository;
    
    public List<CustomerRequest> findAll() {
        return customerRequestRepository.findByOrderByCreatedAtDesc();
    }
    
    public CustomerRequest findById(Long id) {
        return customerRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
    }
    
    public List<CustomerRequest> findByStatus(String status) {
        return customerRequestRepository.findByStatus(status);
    }
    
    public CustomerRequest save(CustomerRequest request) {
        return customerRequestRepository.save(request);
    }
    
    public CustomerRequest update(Long id, CustomerRequest request) {
        CustomerRequest existing = findById(id);
        existing.setStatus(request.getStatus());
        existing.setResponse(request.getResponse());
        existing.setEmployee(request.getEmployee());
        return customerRequestRepository.save(existing);
    }
    
    public void delete(Long id) {
        customerRequestRepository.deleteById(id);
    }
}

