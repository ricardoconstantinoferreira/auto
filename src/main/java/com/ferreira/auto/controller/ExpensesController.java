package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ExpensesDto;
import com.ferreira.auto.dto.ExpensesResponseDto;
import com.ferreira.auto.entity.Expenses;
import com.ferreira.auto.exception.ValueEmptyNullException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auto/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<ExpensesResponseDto> save(@RequestBody ExpensesDto expensesDto) {

        if (expensesDto.getValue() == null || expensesDto.getValue() == 0) {
            throw new ValueEmptyNullException(
                    messageInternationalization.getMessage("expenses.value.required"),
                    "expenses"
            );
        }

        Expenses saved = expensesService.save(expensesDto);
        Optional<Expenses> savedExpenses = Optional.ofNullable(saved);

        ExpensesResponseDto expensesResponseDto = new ExpensesResponseDto(
                messageInternationalization.getMessage("expenses.saved.value"), "200", savedExpenses
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(expensesResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<Expenses>> getAll() {
        return new ResponseEntity<>(expensesService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expensesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
