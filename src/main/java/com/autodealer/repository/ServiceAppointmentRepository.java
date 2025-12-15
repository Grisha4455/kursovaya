package com.autodealer.repository;

import com.autodealer.entity.ServiceAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceAppointmentRepository extends JpaRepository<ServiceAppointment, Long> {
    List<ServiceAppointment> findByCustomerId(Long customerId);
    List<ServiceAppointment> findByStatus(String status);
}

