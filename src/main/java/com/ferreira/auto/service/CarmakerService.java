package com.ferreira.auto.service;

import com.ferreira.auto.dto.CarmakerDto;
import com.ferreira.auto.entity.Carmaker;

import java.util.List;

public interface CarmakerService {

    Carmaker save(CarmakerDto carmakerDto);
    Carmaker getById(Long id);
    List<Carmaker> getAll();
    void delete(Long id);
    boolean hasCarmakerByDescription(String description);
}
