package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.AddressDto;
import com.ferreira.auto.entity.Address;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.AddressRepository;
import com.ferreira.auto.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AddressServiceImpl service;

    @Test
    void saveMapsDtoAndPersists() {
        AddressDto dto = new AddressDto();
        dto.setId(1L);
        dto.setAddress("Street");
        dto.setNumber("10");
        dto.setComplement("A");
        dto.setZipcode("123");
        dto.setNeighborhood("N");
        dto.setCity("C");
        dto.setState("S");
        dto.setCustomerId(9L);

        Customer customer = new Customer();
        when(customerService.getById(9L)).thenReturn(customer);
        when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address result = service.save(dto);

        assertEquals(1L, result.getId());
        assertEquals("Street", result.getAddress());
        assertSame(customer, result.getCustomer());
    }

    @Test
    void hasAddressByCustomerReturnsTrueWhenSameZipAndNumber() {
        AddressDto dto = new AddressDto();
        dto.setCustomerId(2L);
        dto.setZipcode("123");
        dto.setNumber("44");

        Address address = new Address();
        address.setZipcode("123");
        address.setNumber("44");
        when(addressRepository.getAddressByIdAndCustomerId(2L)).thenReturn(address);

        assertTrue(service.hasAddressByCustomer(dto));
    }

    @Test
    void hasAddressByCustomerReturnsFalseWhenNotFound() {
        AddressDto dto = new AddressDto();
        dto.setCustomerId(2L);
        when(addressRepository.getAddressByIdAndCustomerId(2L)).thenReturn(null);

        assertFalse(service.hasAddressByCustomer(dto));
    }

    @Test
    void hasAddressByCustomerReturnsFalseWhenDifferentFields() {
        AddressDto dto = new AddressDto();
        dto.setCustomerId(2L);
        dto.setZipcode("123");
        dto.setNumber("44");

        Address address = new Address();
        address.setZipcode("999");
        address.setNumber("44");
        when(addressRepository.getAddressByIdAndCustomerId(2L)).thenReturn(address);

        assertFalse(service.hasAddressByCustomer(dto));
    }

    @Test
    void getByIdDelegates() {
        Address address = new Address();
        when(addressRepository.getReferenceById(1L)).thenReturn(address);

        assertSame(address, service.getById(1L));
    }

    @Test
    void getAllDelegates() {
        when(addressRepository.findAll()).thenReturn(List.of(new Address()));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void deleteLoadsAndDeletes() {
        Address address = new Address();
        when(addressRepository.getReferenceById(3L)).thenReturn(address);

        service.delete(3L);

        verify(addressRepository).delete(address);
    }
}
