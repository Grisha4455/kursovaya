package com.autodealer.config;

import com.autodealer.entity.Customer;
import com.autodealer.entity.Employee;
import com.autodealer.repository.CustomerRepository;
import com.autodealer.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null) {
            if (!employee.getActive()) {
                throw new UsernameNotFoundException("User is inactive: " + username);
            }
            return User.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .authorities(Collections.singletonList(
                    new SimpleGrantedAuthority(employee.getRole())
                ))
                .build();
        }
        
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            if (!customer.getActive()) {
                throw new UsernameNotFoundException("User is inactive: " + username);
            }
            return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .authorities(Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
                ))
                .build();
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
}

