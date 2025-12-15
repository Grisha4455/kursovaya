package com.autodealer.repository;

import com.autodealer.entity.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {
    List<CustomerRequest> findByStatus(String status);
    List<CustomerRequest> findByEmployeeId(Long employeeId);
    List<CustomerRequest> findByOrderByCreatedAtDesc();
}

