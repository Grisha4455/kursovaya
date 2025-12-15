package com.autodealer.service;

import com.autodealer.entity.ServiceAppointment;
import com.autodealer.repository.ServiceAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceAppointmentService {
    
    private final ServiceAppointmentRepository serviceAppointmentRepository;
    
    public List<ServiceAppointment> findAll() {
        return serviceAppointmentRepository.findAll();
    }
    
    public ServiceAppointment findById(Long id) {
        return serviceAppointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Сервисное обслуживание не найдено"));
    }
    
    public List<ServiceAppointment> findByCustomerId(Long customerId) {
        return serviceAppointmentRepository.findByCustomerId(customerId);
    }
    
    public List<ServiceAppointment> findByStatus(String status) {
        return serviceAppointmentRepository.findByStatus(status);
    }
    
    public ServiceAppointment save(ServiceAppointment serviceAppointment) {
        return serviceAppointmentRepository.save(serviceAppointment);
    }
    
    public ServiceAppointment updateStatus(Long id, String status) {
        ServiceAppointment appointment = findById(id);
        appointment.setStatus(status);
        return serviceAppointmentRepository.save(appointment);
    }
    
    public void delete(Long id) {
        serviceAppointmentRepository.deleteById(id);
    }
}

