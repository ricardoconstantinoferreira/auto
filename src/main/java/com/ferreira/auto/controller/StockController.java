package com.ferreira.auto.controller;

import com.ferreira.auto.dto.StockDto;
import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auto/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAll() {
        return new ResponseEntity<>(stockService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable Long id) {
        Stock s = stockService.getById(id);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping("/by-model/{modelId}")
    public ResponseEntity<Stock> getByModel(@PathVariable Long modelId) {
        return new ResponseEntity<>(stockService.getByModelId(modelId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody StockDto stockDto) {
        Stock saved = stockService.update(id, stockDto);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stockService.delete(id);
    }
}
