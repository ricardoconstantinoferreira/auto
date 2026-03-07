package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CarmakerDto;
import com.ferreira.auto.dto.CarmakerResponseDto;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.exception.CarmakerAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CarmakerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarmakerControllerTest {

    @Mock
    private CarmakerService carmakerService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private CarmakerController controller;

    @Test
    void saveThrowsWhenAlreadyExists() {
        CarmakerDto dto = new CarmakerDto();
        dto.setDescription("Ford");

        when(carmakerService.hasCarmakerByDescription("Ford")).thenReturn(true);
        when(messageInternationalization.getMessage("carmaker.exists.message")).thenReturn("exists");

        assertThrows(CarmakerAlreadyExistsException.class, () -> controller.save(dto));
    }

    @Test
    void saveReturnsCreated() {
        CarmakerDto dto = new CarmakerDto();
        dto.setDescription("Ford");
        Carmaker entity = new Carmaker();

        when(carmakerService.hasCarmakerByDescription("Ford")).thenReturn(false);
        when(carmakerService.save(dto)).thenReturn(entity);
        when(messageInternationalization.getMessage("carmaker.save.message")).thenReturn("saved");

        ResponseEntity<CarmakerResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("saved", response.getBody().message());
    }

    @Test
    void updateSetsIdAndReturnsCreated() {
        CarmakerDto dto = new CarmakerDto();
        when(carmakerService.save(dto)).thenReturn(new Carmaker());
        when(messageInternationalization.getMessage("carmaker.updated.message")).thenReturn("updated");

        ResponseEntity<CarmakerResponseDto> response = controller.update(7L, dto);

        assertEquals(7L, dto.getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void deleteDelegates() {
        controller.delete(3L);
        verify(carmakerService).delete(3L);
    }

    @Test
    void getByIdReturnsOk() {
        Carmaker entity = new Carmaker();
        when(carmakerService.getById(1L)).thenReturn(entity);

        ResponseEntity<Carmaker> response = controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(entity, response.getBody());
    }

    @Test
    void getAllReturnsOk() {
        when(carmakerService.getAll()).thenReturn(List.of(new Carmaker()));

        ResponseEntity<List<Carmaker>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
