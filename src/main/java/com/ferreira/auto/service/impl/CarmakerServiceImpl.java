package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CarmakerDto;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.repository.CarmakerRepository;
import com.ferreira.auto.service.CarmakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarmakerServiceImpl implements CarmakerService {

    @Autowired
    private CarmakerRepository carmakerRepository;

    @Override
    public Carmaker save(CarmakerDto carmakerDto) {
        Carmaker carmaker = new Carmaker();

        if (carmakerDto.getId() != null) {
            carmaker.setId(carmakerDto.getId());
        }
        carmaker.setDescription(carmakerDto.getDescription());
        return carmakerRepository.save(carmaker);
    }

    @Override
    public Carmaker getById(Long id) {
        return carmakerRepository.getReferenceById(id);
    }

    @Override
    public List<Carmaker> getAll() {
        return carmakerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Carmaker carmaker = this.getById(id);
        carmakerRepository.delete(carmaker);
    }

    @Override
    public boolean hasCarmakerByDescription(String description) {
        Optional<Carmaker> carmaker = carmakerRepository.findByDescription(description);
        return !carmaker.isEmpty();
    }
}
