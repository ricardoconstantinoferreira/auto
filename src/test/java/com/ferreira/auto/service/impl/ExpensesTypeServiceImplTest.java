package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ExpensesTypeDto;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.repository.ExpensesTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpensesTypeServiceImplTest {

    @Mock
    private ExpensesTypeRepository repository;

    @InjectMocks
    private ExpensesTypeServiceImpl service;

    @Test
    void savePersistsEntity() {
        ExpensesTypeDto dto = new ExpensesTypeDto();
        dto.setDescription("Fuel");

        ExpensesType saved = new ExpensesType();
        saved.setId(1L);
        saved.setDescription("Fuel");

        when(repository.save(any(ExpensesType.class))).thenReturn(saved);

        ExpensesType result = service.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fuel", result.getDescription());
    }

    @Test
    void updateReturnsNullWhenNotFound() {
        ExpensesTypeDto dto = new ExpensesTypeDto();
        dto.setDescription("X");

        when(repository.findById(10L)).thenReturn(Optional.empty());

        ExpensesType result = service.update(10L, dto);

        assertNull(result);
    }

    @Test
    void updateReturnsUpdatedEntity() {
        ExpensesTypeDto dto = new ExpensesTypeDto();
        dto.setDescription("Updated");

        ExpensesType existing = new ExpensesType();
        existing.setId(2L);
        existing.setDescription("Old");

        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        ExpensesType result = service.update(2L, dto);

        assertNotNull(result);
        assertEquals("Updated", result.getDescription());
    }

    @Test
    void getByIdReturnsEntity() {
        ExpensesType et = new ExpensesType();
        et.setId(3L);
        et.setDescription("Toll");

        when(repository.findById(3L)).thenReturn(Optional.of(et));

        ExpensesType result = service.getById(3L);

        assertNotNull(result);
        assertEquals("Toll", result.getDescription());
    }

    @Test
    void getAllReturnsList() {
        ExpensesType et = new ExpensesType();
        et.setId(4L);
        et.setDescription("Parking");

        when(repository.findAll()).thenReturn(List.of(et));

        List<ExpensesType> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void deleteDelegates() {
        doNothing().when(repository).deleteById(5L);

        service.delete(5L);

        verify(repository).deleteById(5L);
    }
}
