package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CarmakerDto;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.repository.CarmakerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarmakerServiceImplTest {

    @Mock
    private CarmakerRepository carmakerRepository;

    @InjectMocks
    private CarmakerServiceImpl service;

    @Test
    void saveMapsDtoWithId() {
        CarmakerDto dto = new CarmakerDto();
        dto.setId(4L);
        dto.setDescription("VW");
        when(carmakerRepository.save(any(Carmaker.class))).thenAnswer(i -> i.getArgument(0));

        Carmaker saved = service.save(dto);

        assertEquals(4L, saved.getId());
        assertEquals("VW", saved.getDescription());
    }

    @Test
    void getByIdDelegates() {
        Carmaker carmaker = new Carmaker();
        when(carmakerRepository.getReferenceById(1L)).thenReturn(carmaker);
        assertSame(carmaker, service.getById(1L));
    }

    @Test
    void getAllDelegates() {
        when(carmakerRepository.findAll()).thenReturn(List.of(new Carmaker()));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void deleteLoadsAndDeletes() {
        Carmaker carmaker = new Carmaker();
        when(carmakerRepository.getReferenceById(2L)).thenReturn(carmaker);

        service.delete(2L);

        verify(carmakerRepository).delete(carmaker);
    }

    @Test
    void hasCarmakerByDescriptionReturnsTrueWhenFound() {
        when(carmakerRepository.findByDescription("Ford")).thenReturn(Optional.of(new Carmaker()));
        assertTrue(service.hasCarmakerByDescription("Ford"));
    }

    @Test
    void hasCarmakerByDescriptionReturnsFalseWhenEmpty() {
        when(carmakerRepository.findByDescription("Ford")).thenReturn(Optional.empty());
        assertFalse(service.hasCarmakerByDescription("Ford"));
    }
}
