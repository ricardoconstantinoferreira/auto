package com.ferreira.auto.service;

import com.ferreira.auto.dto.ExpensesDto;
import com.ferreira.auto.entity.Expenses;

import java.util.List;

public interface ExpensesService {

    Expenses save(ExpensesDto expensesDto);

    List<Expenses> getAll();

    void delete(Long id);
}
