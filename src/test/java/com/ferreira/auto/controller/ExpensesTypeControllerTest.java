package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ExpensesTypeDto;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.service.ExpensesTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ExpensesTypeControllerTest {

    @Mock
    private ExpensesTypeService service;

    @InjectMocks
    private ExpensesTypeController controller;

    @Test
    void saveReturnsCreated() {
        ExpensesTypeDto dto = new ExpensesTypeDto();
        dto.setDescription("Fuel");

        ExpensesType saved = new ExpensesType();
        saved.setId(1L);
        saved.setDescription("Fuel");

        when(service.save(dto)).thenReturn(saved);

        ResponseEntity<ExpensesType> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getByIdReturnsOk() {
        ExpensesType et = new ExpensesType();
        et.setId(2L);
        et.setDescription("Parking");

        when(service.getById(2L)).thenReturn(et);

        ResponseEntity<ExpensesType> response = controller.getById(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Parking", response.getBody().getDescription());
    }

    @Test
    void getAllReturnsOk() {
        ExpensesType et = new ExpensesType();
        et.setId(3L);
        et.setDescription("Toll");

        when(service.getAll()).thenReturn(List.of(et));

        ResponseEntity<List<ExpensesType>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateReturnsOk() {
        ExpensesTypeDto dto = new ExpensesTypeDto();
        dto.setDescription("Maintenance");

        ExpensesType updated = new ExpensesType();
        updated.setId(4L);
        updated.setDescription("Maintenance");

        when(service.update(4L, dto)).thenReturn(updated);

        ResponseEntity<ExpensesType> response = controller.update(4L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Maintenance", response.getBody().getDescription());
    }

    @Test
    void deleteReturnsNoContent() {
        doNothing().when(service).delete(5L);

        ResponseEntity<Void> response = controller.delete(5L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
