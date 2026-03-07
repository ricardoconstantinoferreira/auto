package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AddressDto;
import com.ferreira.auto.dto.AddressResponseDto;
import com.ferreira.auto.entity.Address;
import com.ferreira.auto.exception.AddressAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.AddressService;
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
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private AddressController controller;

    @Test
    void saveThrowsWhenAddressAlreadyExists() {
        AddressDto dto = new AddressDto();
        dto.setZipcode("12345");

        when(addressService.hasAddressByCustomer(dto)).thenReturn(true);
        when(messageInternationalization.getMessage("address.exists.message")).thenReturn("exists");

        assertThrows(AddressAlreadyExistsException.class, () -> controller.save(dto));
    }

    @Test
    void saveReturnsCreated() {
        AddressDto dto = new AddressDto();
        Address address = new Address();

        when(addressService.hasAddressByCustomer(dto)).thenReturn(false);
        when(addressService.save(dto)).thenReturn(address);
        when(messageInternationalization.getMessage("address.save.message")).thenReturn("saved");

        ResponseEntity<AddressResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("saved", response.getBody().message());
    }

    @Test
    void updateReturnsCreatedAndSetsId() {
        AddressDto dto = new AddressDto();
        Address address = new Address();

        when(addressService.save(dto)).thenReturn(address);
        when(messageInternationalization.getMessage("address.updated.message")).thenReturn("updated");

        ResponseEntity<AddressResponseDto> response = controller.update(10L, dto);

        assertEquals(10L, dto.getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("updated", response.getBody().message());
    }

    @Test
    void getByIdReturnsOk() {
        Address address = new Address();
        when(addressService.getById(2L)).thenReturn(address);

        ResponseEntity<Address> response = controller.getById(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(address, response.getBody());
    }

    @Test
    void getAllReturnsOk() {
        List<Address> addresses = List.of(new Address());
        when(addressService.getAll()).thenReturn(addresses);

        ResponseEntity<List<Address>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deleteDelegatesToService() {
        controller.delete(5L);
        verify(addressService).delete(5L);
    }
}
