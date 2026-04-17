package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ExpensesDto;
import com.ferreira.auto.entity.Expenses;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.repository.ExpensesRepository;
import com.ferreira.auto.repository.ExpensesTypeRepository;
import com.ferreira.auto.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpensesServiceImplTest {

    @Mock
    private ExpensesRepository expensesRepository;

    @Mock
    private ExpensesTypeRepository expensesTypeRepository;

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private ExpensesServiceImpl service;

    @Test
    void saveMapsRecordAndPersists() {
        ExpensesDto dto = new ExpensesDto();
        dto.setExpensesTypeId(2L);
        dto.setModelId(3L);
        dto.setValue(99.9f);

        ExpensesType et = new ExpensesType();
        et.setId(2L);

        Model model = new Model();
        model.setId(3L);

        Expenses saved = new Expenses();
        saved.setId(5L);

        when(expensesTypeRepository.findById(2L)).thenReturn(Optional.of(et));
        when(modelRepository.findById(3L)).thenReturn(Optional.of(model));
        when(expensesRepository.save(any(Expenses.class))).thenReturn(saved);

        Expenses result = service.save(dto);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        verify(expensesRepository).save(any(Expenses.class));
    }

    @Test
    void getAllDelegates() {
        Expenses e = new Expenses();
        e.setId(7L);
        when(expensesRepository.findAll()).thenReturn(List.of(e));

        List<Expenses> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void deleteDelegatesWhenFound() {
        Expenses e = new Expenses();
        e.setId(8L);

        when(expensesRepository.findById(8L)).thenReturn(Optional.of(e));
        doNothing().when(expensesRepository).delete(e);

        service.delete(8L);

        verify(expensesRepository).delete(e);
    }
}
