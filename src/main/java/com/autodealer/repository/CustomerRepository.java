package com.autodealer.repository;

import com.autodealer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFullNameContaining(String name);
    List<Customer> findByPhone(String phone);
    Customer findByUsername(String username);
}

