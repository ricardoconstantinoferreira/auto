package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ExpensesTypeDto;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.repository.ExpensesTypeRepository;
import com.ferreira.auto.service.ExpensesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpensesTypeServiceImpl implements ExpensesTypeService {

    @Autowired
    private ExpensesTypeRepository repository;

    @Override
    public ExpensesType save(ExpensesTypeDto expensesTypeDto) {
        ExpensesType et = new ExpensesType();
        et.setDescription(expensesTypeDto.getDescription());
        return repository.save(et);
    }

    @Override
    public ExpensesType update(Long id, ExpensesTypeDto expensesTypeDto) {
        Optional<ExpensesType> opt = repository.findById(id);
        if (opt.isEmpty()) return null;
        ExpensesType et = opt.get();
        et.setDescription(expensesTypeDto.getDescription());
        return repository.save(et);
    }

    @Override
    public ExpensesType getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ExpensesType> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
