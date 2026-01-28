package com.ferreira.auto.service;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(CustomerDto customerDto);
    Customer getById(Long id);
    List<Customer> getAll();
    boolean delete(Long id);
    void saveToken(Customer customer, String token);
    Customer getCustomerByEmail(String email);
    boolean hasCustomerByEmail(CustomerDto customerDto);
    boolean hasCustomerByDocument(CustomerDto customerDto);
}
