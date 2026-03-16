package com.ferreira.auto.controller;

import com.ferreira.auto.dto.RentalDto;
import com.ferreira.auto.entity.Rental;
import com.ferreira.auto.exception.RentalAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auto/rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<Rental> save(@RequestBody RentalDto rentalDto) {

        List<Rental> rental = rentalService.getAll();

        if (rental.size() > 0) {
            throw new RentalAlreadyExistsException(messageInternationalization.getMessage("rental.already"), "rental");
        }

        Rental saved = rentalService.save(rentalDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getAll() {
        return new ResponseEntity<>(rentalService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getById(@PathVariable Long id) {
        return new ResponseEntity<>(rentalService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> update(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.update(id, rentalDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rentalService.delete(id);
    }
}
