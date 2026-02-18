package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.dto.ModelResponseDto;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.exception.ModelAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.infra.upload.Config;
import com.ferreira.auto.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/model")
public class ModelController {

    private final Path root = Paths.get(Config.UPLOADS);

    @Autowired
    private ModelService modelService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ModelResponseDto> save(@ModelAttribute ModelRecord modelRecord) throws IOException {
        if (modelService.hasModel(modelRecord.description())) {
            throw new ModelAlreadyExistsException(
                    messageInternationalization.getMessage("model.exists.message"),
                    modelRecord.description()
            );
        }

        if (!Files.exists(root)) Files.createDirectory(root);

        Model entity = modelService.save(modelRecord);

        if (entity != null) {
            Files.copy(modelRecord.image().getInputStream(),
                    this.root.resolve(modelRecord.image().getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        Optional<Model> optionalModel = Optional.ofNullable(entity);
        ModelResponseDto modelResponseDto = new ModelResponseDto(
                messageInternationalization.getMessage("model.save.message"), "200", optionalModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelResponseDto);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ModelResponseDto> update(
            @PathVariable(value = "id") Long id,
            @ModelAttribute ModelRecord modelRecord
    ) {
        ModelRecord record = new ModelRecord(id, modelRecord.description(),
                modelRecord.carmakerId(), modelRecord.year(), modelRecord.active(),
                modelRecord.image());

        Model entity = modelService.save(record);
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
