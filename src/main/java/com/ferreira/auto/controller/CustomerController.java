package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auto/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.save(customerDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
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
