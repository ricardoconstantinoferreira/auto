package com.ferreira.auto.service;

import com.ferreira.auto.dto.ExpensesTypeDto;
import com.ferreira.auto.entity.ExpensesType;

import java.util.List;

public interface ExpensesTypeService {
    ExpensesType save(ExpensesTypeDto expensesTypeDto);
    ExpensesType update(Long id, ExpensesTypeDto expensesTypeDto);
    ExpensesType getById(Long id);
    List<ExpensesType> getAll();
    void delete(Long id);
}
