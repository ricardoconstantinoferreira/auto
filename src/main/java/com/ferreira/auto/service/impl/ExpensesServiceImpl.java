package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ExpensesDto;
import com.ferreira.auto.entity.Expenses;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.repository.ExpensesRepository;
import com.ferreira.auto.repository.ExpensesTypeRepository;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesTypeRepository expensesTypeRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Expenses save(ExpensesDto expensesDto) {
        ExpensesType expensesType = expensesTypeRepository.findById(expensesDto.getExpensesTypeId())
                .orElse(null);

        Model model = modelRepository.findById(expensesDto.getModelId())
                .orElse(null);

        Expenses expenses = new Expenses();
        expenses.setExpensesType(expensesType);
        expenses.setModel(model);
        expenses.setValue(expensesDto.getValue());
        expenses.setDateExpenses(LocalDateTime.now());

        return expensesRepository.save(expenses);
    }

    @Override
    public List<Expenses> getAll() {
        return expensesRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        expensesRepository.findById(id).ifPresent(expenses -> expensesRepository.delete(expenses));
    }
}
