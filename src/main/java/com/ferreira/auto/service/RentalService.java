package com.ferreira.auto.service;

import com.ferreira.auto.dto.RentalDto;
import com.ferreira.auto.entity.Rental;

import java.util.List;

public interface RentalService {

    Rental save(RentalDto rentalDto);
    Rental getRental();
    Rental update(Long id, RentalDto rentalDto);
}
