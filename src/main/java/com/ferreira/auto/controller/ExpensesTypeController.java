package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ExpensesTypeDto;
import com.ferreira.auto.entity.ExpensesType;
import com.ferreira.auto.service.ExpensesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auto/expenses-type")
public class ExpensesTypeController {

    @Autowired
    private ExpensesTypeService expensesTypeService;

    @PostMapping
    public ResponseEntity<ExpensesType> save(@RequestBody ExpensesTypeDto expensesTypeDto) {
        ExpensesType saved = expensesTypeService.save(expensesTypeDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpensesType> getById(@PathVariable Long id) {
        ExpensesType et = expensesTypeService.getById(id);
        if (et == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(et, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ExpensesType>> getAll() {
        return new ResponseEntity<>(expensesTypeService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpensesType> update(@PathVariable Long id, @RequestBody ExpensesTypeDto expensesTypeDto) {
        ExpensesType updated = expensesTypeService.update(id, expensesTypeDto);
        if (updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expensesTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
