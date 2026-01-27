package com.ferreira.auto.service;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        if (customerDto.getId() == null) {
            customer.setActive(true);
        } else {
            customer.setId(customerDto.getId());
            customer.setActive(customerDto.isActive());
        }

        return customerRepository.save(customer);
    }

    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public boolean delete(Long id) {
        Customer customer = this.getById(id);

        if (customer != null) {
            customer.setActive(false);
            customer = customerRepository.save(customer);
        }

        return customer.isActive();
    }

    public void saveToken(Customer customer, String token) {
        customer.setToken(token);
        customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            return null;
        }

        return customerRepository.findByEmail(email).get();
    }

    public boolean hasCustomerByEmail(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerDto.getEmail());

        if (!customerOptional.isEmpty()) {
            return true;
        }

        return false;
    }

    public boolean hasCustomerByDocument(CustomerDto customerDto) {
        Optional<Customer> customerDocumentOptional = customerRepository.findByDocument(customerDto.getDocument());

        if (!customerDocumentOptional.isEmpty()) {
            return true;
        }

        return false;
    }

}
