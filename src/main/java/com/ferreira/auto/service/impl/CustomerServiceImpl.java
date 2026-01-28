package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.CustomerRepository;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(CustomerDto customerDto) {

        String password = new BCryptPasswordEncoder().encode(customerDto.getPassword());

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setDocument(customerDto.getDocument());
        customer.setCustomerType(customerDto.getCustomerType());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(password);

        if (customerDto.getId() == null) {
            customer.setActive(true);
        } else {
            customer.setId(customerDto.getId());
            customer.setActive(customerDto.isActive());
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        Customer customer = this.getById(id);

        if (customer != null) {
            customer.setActive(false);
            customer = customerRepository.save(customer);
        }

        return customer.isActive();
    }

    @Override
    public void saveToken(Customer customer, String token) {
        customer.setToken(token);
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            return null;
        }

        return customerRepository.findByEmail(email).get();
    }

    @Override
    public boolean hasCustomerByEmail(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerDto.getEmail());
        return !customerOptional.isEmpty();
    }

    @Override
    public boolean hasCustomerByDocument(CustomerDto customerDto) {
        Optional<Customer> customerDocumentOptional = customerRepository.findByDocument(customerDto.getDocument());
        return !customerDocumentOptional.isEmpty();
    }

}
