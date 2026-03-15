package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.lib.ModelInterface;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.service.CarmakerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceImplTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private CarmakerService carmakerService;

    @InjectMocks
    private ModelServiceImpl service;

    @Test
    void saveMapsRecordAndPersists() {
        MockMultipartFile file = new MockMultipartFile("image", "a.png", "image/png", "x".getBytes());
        // ModelRecord signature: Long id, String description, Long carmakerId, Long categoryId, int year, boolean active, MultipartFile image, float price, Integer qtde
        ModelRecord record = new ModelRecord(1L, "M", 3L, 4L, 2024, true, file, 100f, 10);
        Carmaker carmaker = new Carmaker();
        when(carmakerService.getById(3L)).thenReturn(carmaker);
        when(modelRepository.save(any(Model.class))).thenAnswer(i -> i.getArgument(0));

        Model model = service.save(record);

        assertEquals(1L, model.getId());
        assertEquals("M", model.getDescription());
        assertTrue(model.isActive());
        assertSame(carmaker, model.getCarmaker());
        assertEquals("uploads/a.png", model.getImage());
    }

    @Test
    void getByIdReturnsEntity() {
        Model model = new Model();
        when(modelRepository.findById(8L)).thenReturn(Optional.of(model));
        assertSame(model, service.getById(8L));
    }

    @Test
    void getAllDelegatesToCustomQuery() {
        // getAllModels returns List<ModelInterface>
        when(modelRepository.getAllModels()).thenReturn(List.of(mock(ModelInterface.class)));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void hasModelTrueAndFalse() {
        when(modelRepository.findByDescription("X")).thenReturn(Optional.of(new Model()));
        assertTrue(service.hasModel("X"));

        when(modelRepository.findByDescription("X")).thenReturn(Optional.empty());
        assertFalse(service.hasModel("X"));
    }

    @Test
    void inactivateSetsFalseAndSaves() {
        Model model = new Model();
        model.setActive(true);
        when(modelRepository.findById(1L)).thenReturn(Optional.of(model));

        service.inactivate(1L);

        assertFalse(model.isActive());
        verify(modelRepository).save(model);
    }

    @Test
    void activateSetsTrueAndSaves() {
        Model model = new Model();
        model.setActive(false);
        when(modelRepository.findById(1L)).thenReturn(Optional.of(model));

        service.activate(1L);

        assertTrue(model.isActive());
        verify(modelRepository).save(model);
    }
}
