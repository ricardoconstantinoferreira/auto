package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ExpensesDto;
import com.ferreira.auto.dto.ExpensesResponseDto;
import com.ferreira.auto.entity.Expenses;
import com.ferreira.auto.exception.ValueEmptyNullException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.ExpensesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpensesControllerTest {

    @Mock
    private ExpensesService expensesService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private ExpensesController controller;

    @Test
    void saveThrowsWhenValueNull() {
        ExpensesDto dto = new ExpensesDto();
        dto.setExpensesTypeId(1L);
        dto.setModelId(2L);
        dto.setValue(null);

        when(messageInternationalization.getMessage("expenses.value.required")).thenReturn("Expenses value is required!");

        ValueEmptyNullException ex = assertThrows(ValueEmptyNullException.class, () -> controller.save(dto));
        assertEquals("Expenses value is required!", ex.getMessage());
    }

    @Test
    void saveReturnsCreatedWhenValuePresent() {
        ExpensesDto dto = new ExpensesDto();
        dto.setExpensesTypeId(1L);
        dto.setModelId(2L);
        dto.setValue(150.5f);

        Expenses saved = new Expenses();
        saved.setId(10L);

        when(expensesService.save(dto)).thenReturn(saved);
        when(messageInternationalization.getMessage("expenses.saved.value")).thenReturn("Expenses value created successfully!");

        ResponseEntity<ExpensesResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Expenses value created successfully!", response.getBody().message());
        assertTrue(response.getBody().expenses().isPresent());
        assertEquals(10L, response.getBody().expenses().get().getId());
    }
}
