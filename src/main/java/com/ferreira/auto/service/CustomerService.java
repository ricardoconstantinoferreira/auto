package com.ferreira.auto.service;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(CustomerDto customerDto) {

        String password = new BCryptPasswordEncoder().encode(customerDto.getPassword());

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setDocument(customerDto.getDocument());
        customer.setCustomerType(customerDto.getCustomerType());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(password);
        return customerRepository.save(customer);
    }

    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
    }

    public void saveToken(Customer customer, String token) {
        customer.setToken(token);
        customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).get();
    }

}
