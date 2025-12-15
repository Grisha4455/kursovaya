package com.autodealer.service;

import com.autodealer.entity.Supplier;
import com.autodealer.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {
    
    private final SupplierRepository supplierRepository;
    
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }
    
    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Поставщик не найден"));
    }
    
    public List<Supplier> searchByName(String name) {
        return supplierRepository.findByNameContaining(name);
    }
    
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    
    public Supplier update(Long id, Supplier supplier) {
        Supplier existing = findById(id);
        existing.setName(supplier.getName());
        existing.setContactPerson(supplier.getContactPerson());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());
        existing.setAddress(supplier.getAddress());
        return supplierRepository.save(existing);
    }
    
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }
}

