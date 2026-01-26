package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auto/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.save(customerDto), HttpStatus.OK);
    }
}
