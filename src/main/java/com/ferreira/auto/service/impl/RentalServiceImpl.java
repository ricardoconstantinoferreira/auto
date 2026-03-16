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
    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rental not found"));
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

    @Override
    public void delete(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rental not found"));
        rentalRepository.delete(rental);
    }
}
