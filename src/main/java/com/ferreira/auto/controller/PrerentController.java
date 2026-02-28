package com.ferreira.auto.controller;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.dto.PrerentResponseDto;
import com.ferreira.auto.entity.Prerent;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.PrerentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/prerent")
public class PrerentController {

    @Autowired
    private PrerentService prerentService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<PrerentResponseDto> save(@RequestBody PrerentDto prerentDto) {
        Prerent prerent = prerentService.save(prerentDto);
        Optional<Prerent> optionalPrerent = Optional.ofNullable(prerent);
        PrerentResponseDto prerentResponseDto = new PrerentResponseDto(
                messageInternationalization.getMessage( "prerent.add.car"),
                "200",
                optionalPrerent);

        return ResponseEntity.status(HttpStatus.CREATED).body(prerentResponseDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Prerent> findByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return new ResponseEntity<>(prerentService.findByCustomerId(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/{modelId}")
    public void deleteByModelId(@PathVariable(value = "modelId") Long modelId) {
        prerentService.deleteByModelId(modelId);
    }

}
