package com.autodealer.repository;

import com.autodealer.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    
    List<CarModel> findByBrand(String brand);
    
    List<CarModel> findByColor(String color);
    
    List<CarModel> findByTransmission(String transmission);
    
    List<CarModel> findByDoors(Integer doors);
    
    @Query("SELECT DISTINCT c FROM CarModel c WHERE " +
           "(:brand IS NULL OR :brand = '' OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
           "(:color IS NULL OR :color = '' OR LOWER(c.color) LIKE LOWER(CONCAT('%', :color, '%'))) AND " +
           "(:transmission IS NULL OR :transmission = '' OR LOWER(c.transmission) LIKE LOWER(CONCAT('%', :transmission, '%'))) AND " +
           "(:doors IS NULL OR c.doors = :doors) AND " +
           "(:minPower IS NULL OR c.enginePower >= :minPower) AND " +
           "(:maxPower IS NULL OR c.enginePower <= :maxPower)")
    List<CarModel> findByFilters(
        @Param("brand") String brand,
        @Param("color") String color,
        @Param("transmission") String transmission,
        @Param("doors") Integer doors,
        @Param("minPower") Integer minPower,
        @Param("maxPower") Integer maxPower
    );
}

