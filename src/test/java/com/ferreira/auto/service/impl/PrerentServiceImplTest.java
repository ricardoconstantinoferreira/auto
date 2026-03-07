package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.dto.PrerentResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Prerent;
import com.ferreira.auto.entity.StatusPrerent;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.repository.PrerentRepository;
import com.ferreira.auto.service.CustomerService;
import com.ferreira.auto.service.ModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrerentServiceImplTest {

    @Mock
    private PrerentRepository prerentRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelService modelService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private PrerentServiceImpl service;

    @Test
    void saveReturnsResponseDto() {
        PrerentDto dto = new PrerentDto();
        dto.setCustomerId(1L);
        dto.setModelId(2L);

        Customer customer = new Customer();
        Model model = new Model();
        Prerent persisted = new Prerent();

        when(customerService.getById(1L)).thenReturn(customer);
        when(modelService.getById(2L)).thenReturn(model);
        when(prerentRepository.save(any(Prerent.class))).thenReturn(persisted);
        when(messageInternationalization.getMessage("prerent.add.car")).thenReturn("added");

        PrerentResponseDto response = service.save(dto);

        assertEquals("added", response.message());
        assertTrue(response.prerent().isPresent());
    }

    @Test
    void findByCustomerIdDelegates() {
        when(prerentRepository.findByCustomerId(1L)).thenReturn(List.of(new Prerent()));
        assertEquals(1, service.findByCustomerId(1L).size());
    }

    @Test
    void deleteByModelIdDelegates() {
        service.deleteByModelId(3L);
        verify(prerentRepository).deleteByModelId(3L);
    }

    @Test
    void validateDataPrerentReturnsTrueWhenFound() {
        PrerentDto dto = new PrerentDto();
        dto.setCustomerId(1L);
        dto.setModelId(2L);
        when(prerentRepository.findByCustomerIdAndModelId(1L, 2L)).thenReturn(new Prerent());

        assertTrue(service.validateDataPrerent(dto));
    }

    @Test
    void validateDataPrerentReturnsFalseWhenNull() {
        PrerentDto dto = new PrerentDto();
        dto.setCustomerId(1L);
        dto.setModelId(2L);
        when(prerentRepository.findByCustomerIdAndModelId(1L, 2L)).thenReturn(null);

        assertFalse(service.validateDataPrerent(dto));
    }

    @Test
    void getQtyPrerentDelegates() {
        when(prerentRepository.findQtyPrerentByCustomer(9L)).thenReturn(4L);
        assertEquals(4L, service.getQtyPrerent(9L));
    }

    @Test
    void changedStatusUpdatesAllWhenNotEmpty() {
        Prerent p1 = new Prerent();
        p1.setStatus(StatusPrerent.IS_OPEN);
        Prerent p2 = new Prerent();
        p2.setStatus(StatusPrerent.IS_OPEN);

        when(prerentRepository.findByCustomerId(4L)).thenReturn(List.of(p1, p2));

        service.changedStatus(4L);

        assertEquals(StatusPrerent.IS_CLOSED, p1.getStatus());
        assertEquals(StatusPrerent.IS_CLOSED, p2.getStatus());
        verify(prerentRepository, times(2)).save(any(Prerent.class));
    }

    @Test
    void changedStatusDoesNothingWhenEmpty() {
        when(prerentRepository.findByCustomerId(4L)).thenReturn(List.of());

        service.changedStatus(4L);

        verify(prerentRepository, never()).save(any());
    }
}
