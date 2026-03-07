package com.ferreira.auto.controller;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.dto.ModelResponseDto;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.exception.ModelAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.infra.upload.Config;
import com.ferreira.auto.service.ModelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {

    @Mock
    private ModelService modelService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private ModelController controller;

    @AfterEach
    void cleanupUploads() throws IOException {
        Path file = Path.of(Config.UPLOADS, "test-model.png");
        Files.deleteIfExists(file);
    }

    @Test
    void saveThrowsWhenDuplicateOnNewRecord() {
        ModelRecord record = new ModelRecord(null, "Model X", 1L, 2024, true,
                new MockMultipartFile("image", "test-model.png", "image/png", "x".getBytes()), 10f);

        when(modelService.hasModel("Model X")).thenReturn(true);
        when(messageInternationalization.getMessage("model.exists.message")).thenReturn("exists");

        assertThrows(ModelAlreadyExistsException.class, () -> controller.save(record));
    }

    @Test
    void saveReturnsCreatedAndCopiesFile() throws IOException {
        ModelRecord record = new ModelRecord(null, "Model X", 1L, 2024, true,
                new MockMultipartFile("image", "test-model.png", "image/png", "abc".getBytes()), 10f);
        Model model = new Model();

        when(modelService.hasModel("Model X")).thenReturn(false);
        when(modelService.save(record)).thenReturn(model);
        when(messageInternationalization.getMessage("model.save.message")).thenReturn("saved");

        ResponseEntity<ModelResponseDto> response = controller.save(record);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(Files.exists(Path.of(Config.UPLOADS, "test-model.png")));
    }

    @Test
    void updateReturnsCreated() {
        MockMultipartFile file = new MockMultipartFile("image", "test-model.png", "image/png", "x".getBytes());
        ModelRecord record = new ModelRecord(null, "Model X", 1L, 2024, true, file, 10f);

        when(modelService.save(any())).thenReturn(new Model());
        when(messageInternationalization.getMessage("model.updated.message")).thenReturn("updated");

        ResponseEntity<ModelResponseDto> response = controller.update(11L, record);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(modelService).save(argThat(r -> r.id().equals(11L)));
    }

    @Test
    void inactivateDelegates() {
        controller.inactivate(4L);
        verify(modelService).inactivate(4L);
    }

    @Test
    void activateDelegates() {
        controller.activate(4L);
        verify(modelService).activate(4L);
    }

    @Test
    void getByIdReturnsOk() {
        Model model = new Model();
        when(modelService.getById(1L)).thenReturn(model);

        ResponseEntity<Model> response = controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(model, response.getBody());
    }

    @Test
    void getAllReturnsOk() {
        when(modelService.getAll()).thenReturn(List.of(new Model()));

        ResponseEntity<List<Model>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
