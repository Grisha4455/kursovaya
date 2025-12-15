package com.autodealer.service;

import com.autodealer.entity.TestDrive;
import com.autodealer.repository.TestDriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestDriveService {
    
    private final TestDriveRepository testDriveRepository;
    
    public List<TestDrive> findAll() {
        return testDriveRepository.findAll();
    }
    
    public TestDrive findById(Long id) {
        return testDriveRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Тест драйв не найден"));
    }
    
    public List<TestDrive> findByCustomerId(Long customerId) {
        return testDriveRepository.findByCustomerId(customerId);
    }
    
    public List<TestDrive> findByStatus(String status) {
        return testDriveRepository.findByStatus(status);
    }
    
    public TestDrive save(TestDrive testDrive) {
        return testDriveRepository.save(testDrive);
    }
    
    public TestDrive updateStatus(Long id, String status) {
        TestDrive testDrive = findById(id);
        testDrive.setStatus(status);
        return testDriveRepository.save(testDrive);
    }
    
    public void delete(Long id) {
        testDriveRepository.deleteById(id);
    }
}

