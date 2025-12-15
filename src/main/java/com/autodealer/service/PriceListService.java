package com.autodealer.service;

import com.autodealer.entity.PriceList;
import com.autodealer.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PriceListService {
    
    private final PriceListRepository priceListRepository;
    private final CarModelService carModelService;
    
    public List<PriceList> findAll() {
        return priceListRepository.findAll();
    }
    
    public PriceList findById(Long id) {
        return priceListRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Цен не найдено"));
    }
    
    public List<PriceList> findByCarModelId(Long carModelId) {
        return priceListRepository.findByCarModelId(carModelId);
    }
    
    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }
    
    public PriceList update(Long id, PriceList priceList) {
        PriceList existing = findById(id);
        existing.setProductionYear(priceList.getProductionYear());
        existing.setBasePrice(priceList.getBasePrice());
        existing.setPreSalePreparation(priceList.getPreSalePreparation());
        existing.setTransportCosts(priceList.getTransportCosts());
        existing.setCarModel(priceList.getCarModel());
        return priceListRepository.save(existing);
    }
    
    public void delete(Long id) {
        priceListRepository.deleteById(id);
    }
}

