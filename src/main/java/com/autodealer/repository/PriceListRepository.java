package com.autodealer.repository;

import com.autodealer.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    List<PriceList> findByCarModelId(Long carModelId);
    List<PriceList> findByProductionYear(Integer productionYear);
}

