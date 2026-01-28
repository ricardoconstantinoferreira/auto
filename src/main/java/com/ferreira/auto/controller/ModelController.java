package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ModelDto;
import com.ferreira.auto.dto.ModelResponseDto;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.exception.ModelAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<ModelResponseDto> save(@RequestBody ModelDto modelDto) {
        if (modelService.hasModel(modelDto.getDescription())) {
            throw new ModelAlreadyExistsException(
                    messageInternationalization.getMessage("model.exists.message"),
                    modelDto.getDescription()
            );
        }

        Model entity = modelService.save(modelDto);
        Optional<Model> optionalModel = Optional.ofNullable(entity);
        ModelResponseDto modelResponseDto = new ModelResponseDto(
                messageInternationalization.getMessage("model.save.message"), "200", optionalModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelResponseDto> update(
            @PathVariable(value = "id") Long id,
            @RequestBody ModelDto modelDto
    ) {
        modelDto.setId(id);

        Model entity = modelService.save(modelDto);
        Optional<Model> optionalModel = Optional.ofNullable(entity);
        ModelResponseDto modelResponseDto = new ModelResponseDto(
                messageInternationalization.getMessage("model.updated.message"), "200", optionalModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelResponseDto);
    }

    @PutMapping("/inactivate/{id}")
    public void inactivate(@PathVariable(value = "id") Long id) {
        modelService.inactivate(id);
    }

    @PutMapping("/activate/{id}")
    public void activate(@PathVariable(value = "id") Long id) {
        modelService.activate(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(modelService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Model>> getAll() {
        return new ResponseEntity<>(modelService.getAll(), HttpStatus.OK);
    }
}
