package com.autodealer.service;

import com.autodealer.entity.CarModel;
import com.autodealer.entity.Supplier;
import com.autodealer.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CarModelService {
    
    private final CarModelRepository carModelRepository;
    private final SupplierService supplierService;
    
    public List<CarModel> findAll() {
        return carModelRepository.findAll();
    }
    
    public CarModel findById(Long id) {
        return carModelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car model not found"));
    }
    
    public List<CarModel> findByFilters(String brand, String color, String transmission, 
                                        Integer doors, Integer minPower, Integer maxPower) {
        return carModelRepository.findByFilters(brand, color, transmission, doors, minPower, maxPower);
    }
    
    public CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }
    
    public CarModel update(Long id, CarModel carModel) {
        CarModel existing = findById(id);
        existing.setName(carModel.getName());
        existing.setBrand(carModel.getBrand());
        existing.setColor(carModel.getColor());
        existing.setUpholstery(carModel.getUpholstery());
        existing.setEngineType(carModel.getEngineType());
        existing.setEnginePower(carModel.getEnginePower());
        existing.setTransmission(carModel.getTransmission());
        existing.setDoors(carModel.getDoors());
        existing.setDescription(carModel.getDescription());
        return carModelRepository.save(existing);
    }
    
    public CarModel addSupplier(Long carModelId, Long supplierId) {
        CarModel carModel = findById(carModelId);
        Supplier supplier = supplierService.findById(supplierId);
        carModel.getSuppliers().add(supplier);
        return carModelRepository.save(carModel);
    }
    
    public CarModel removeSupplier(Long carModelId, Long supplierId) {
        CarModel carModel = findById(carModelId);
        carModel.getSuppliers().removeIf(s -> s.getId().equals(supplierId));
        return carModelRepository.save(carModel);
    }
    
    public void delete(Long id) {
        carModelRepository.deleteById(id);
    }
}

