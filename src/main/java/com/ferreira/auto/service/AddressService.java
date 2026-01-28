package com.ferreira.auto.service;

import com.ferreira.auto.dto.AddressDto;
import com.ferreira.auto.entity.Address;

import java.util.List;

public interface AddressService {

    Address save(AddressDto addressDto);
    Address getById(Long id);
    List<Address> getAll();
    boolean hasAddressByCustomer(AddressDto addressDto);
    void delete(Long id);
}
