package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.RentalDto;
import com.ferreira.auto.entity.Rental;
import com.ferreira.auto.repository.RentalRepository;
import com.ferreira.auto.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Rental save(RentalDto rentalDto) {
        Rental rental = new Rental();
        rental.setQtdeDaysRent(rentalDto.getQtdeDaysRent());
        rental.setPercentageInterest(rentalDto.getPercentageInterest());
        return rentalRepository.save(rental);
    }

    @Override
    public Rental getRental() {
        Rental rental = null;
        List<Rental> rentals = rentalRepository.findAll();

        if (!rentals.isEmpty()) {
            rental = rentals.get(0);
        }
        return rental;
    }


    @Override
    public Rental update(Long id, RentalDto rentalDto) {
        Optional<Rental> opt = rentalRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Rental not found");
        }
        Rental rental = opt.get();
        rental.setQtdeDaysRent(rentalDto.getQtdeDaysRent());
        rental.setPercentageInterest(rentalDto.getPercentageInterest());
        return rentalRepository.save(rental);
    }
}
