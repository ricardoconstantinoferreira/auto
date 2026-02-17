package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CarmakerDto;
import com.ferreira.auto.dto.CarmakerResponseDto;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.exception.CarmakerAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CarmakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/carmaker")
public class CarmakerController {

    @Autowired
    private CarmakerService carmakerService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<CarmakerResponseDto> save(@RequestBody CarmakerDto carmakerDto) {
        if (carmakerService.hasCarmakerByDescription(carmakerDto.getDescription())) {
            throw new CarmakerAlreadyExistsException(
                    messageInternationalization.getMessage("carmaker.exists.message"),
                    carmakerDto.getDescription()
            );
        }

        Carmaker entity = carmakerService.save(carmakerDto);
        Optional<Carmaker> optionalCarmaker = Optional.ofNullable(entity);
        CarmakerResponseDto carmakerResponseDto = new CarmakerResponseDto(
                messageInternationalization.getMessage("carmaker.save.message"), "200", optionalCarmaker
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(carmakerResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarmakerResponseDto> update(@PathVariable(value = "id") Long id,
                                                      @RequestBody CarmakerDto carmakerDto) {
        carmakerDto.setId(id);

        Carmaker entity = carmakerService.save(carmakerDto);
        Optional<Carmaker> optionalCarmaker = Optional.ofNullable(entity);
        CarmakerResponseDto carmakerResponseDto = new CarmakerResponseDto(
                messageInternationalization.getMessage("carmaker.updated.message"), "200", optionalCarmaker
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(carmakerResponseDto);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        carmakerService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carmaker> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(carmakerService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Carmaker>> getAll() {
        return new ResponseEntity<>(carmakerService.getAll(), HttpStatus.OK);
    }
}
