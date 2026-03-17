package com.ferreira.auto.controller;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.dto.PrerentResponseDto;
import com.ferreira.auto.entity.Prerent;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.exception.PrerentAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.PrerentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrerentControllerTest {

    @Mock
    private PrerentService prerentService;

    @Mock
    private com.ferreira.auto.service.StockService stockService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private PrerentController controller;

    @Test
    void saveThrowsWhenDataInvalid() {
        PrerentDto dto = new PrerentDto();
        dto.setModelId(1L);
        when(prerentService.validateDataPrerent(dto)).thenReturn(true);
        when(messageInternationalization.getMessage("prerent.not.car")).thenReturn("invalid");

        assertThrows(PrerentAlreadyExistsException.class, () -> controller.save(dto));
    }

    @Test
    void saveReturnsCreated() {
        PrerentDto dto = new PrerentDto();
        dto.setModelId(1L);
        PrerentResponseDto responseDto = new PrerentResponseDto("ok", "200", Optional.of(new Prerent()));

        // mock validar dados
        when(prerentService.validateDataPrerent(any())).thenReturn(false);

        // mock estoque disponível
        StockInterface stock = mock(StockInterface.class);
        when(stock.getQtde()).thenReturn(5);
        when(stockService.findStockWithModelByModelId(anyLong())).thenReturn(stock);

        when(prerentService.save(any())).thenReturn(responseDto);

        ResponseEntity<PrerentResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("ok", response.getBody().message());
    }

    @Test
    void findByCustomerIdReturnsOk() {
        when(prerentService.findByCustomerId(9L)).thenReturn(List.of(new Prerent()));

        ResponseEntity<List<Prerent>> response = controller.findByCustomerId(9L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deleteByModelIdDelegates() {
        controller.deleteByModelId(2L);
        verify(prerentService).deleteByModelId(2L);
    }

    @Test
    void getQtyPrerentReturnsOk() {
        when(prerentService.getQtyPrerent(10L)).thenReturn(3L);

        ResponseEntity<Long> response = controller.getQtyPrerent(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3L, response.getBody());
    }
}
