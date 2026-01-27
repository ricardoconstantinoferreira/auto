package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.dto.CustomerResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.exception.CustomerAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> save(@RequestBody CustomerDto customerDto) {

        if (customerService.hasCustomerByEmail(customerDto)) {
            throw new CustomerAlreadyExistsException(
                    messageInternationalization.getMessage("customer.exists.message"),
                    customerDto.getEmail()
            );
        }

        if (customerService.hasCustomerByDocument(customerDto)) {
            throw new CustomerAlreadyExistsException(
                    messageInternationalization.getMessage("customer.exists.message"),
                    customerDto.getDocument()
            );
        }

        Customer entity = customerService.save(customerDto);
        Optional<Customer> optionalCustomer = Optional.ofNullable(entity);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(
                messageInternationalization.getMessage("customer.save.message"), "200", optionalCustomer
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable(value = "id") Long id
    ) {
        boolean isActive = customerService.delete(id);

        if (!isActive) {
            return new ResponseEntity<>(
                    messageInternationalization.getMessage("customer.deleted.message"),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                messageInternationalization.getMessage("customer.no.deleted.message"),
                HttpStatus.NOT_FOUND);
    }
}
