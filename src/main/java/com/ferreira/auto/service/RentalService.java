package com.ferreira.auto.service;

import com.ferreira.auto.dto.RentalDto;
import com.ferreira.auto.entity.Rental;

import java.util.List;

public interface RentalService {

    Rental save(RentalDto rentalDto);
    List<Rental> getAll();
    Rental getById(Long id);
    Rental update(Long id, RentalDto rentalDto);
    void delete(Long id);
}
