package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.AddressDto;
import com.ferreira.auto.entity.Address;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.AddressRepository;
import com.ferreira.auto.service.AddressService;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public Address save(AddressDto addressDto) {
        Address address = new Address();

        if (addressDto.getId() != null) {
            address.setId(addressDto.getId());
        }

        address.setAddress(addressDto.getAddress());
        address.setCity(addressDto.getCity());
        address.setComplement(addressDto.getComplement());
        address.setNeighborhood(addressDto.getNeighborhood());
        address.setComplement(addressDto.getComplement());
        address.setNumber(addressDto.getNumber());
        address.setState(addressDto.getState());
        address.setZipcode(addressDto.getZipcode());

        Customer customer = customerService.getById(addressDto.getCustomerId());
        address.setCustomer(customer);

        return addressRepository.save(address);
    }

    @Override
    public boolean hasAddressByCustomer(AddressDto addressDto) {
        Address address = addressRepository.getAddressByIdAndCustomerId(addressDto.getCustomerId());
        if (address != null) {
            if (address.getZipcode().equals(addressDto.getZipcode()) &&
            address.getNumber().equals(addressDto.getNumber())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Address getById(Long id) {
        return addressRepository.getReferenceById(id);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Address address = this.getById(id);
        addressRepository.delete(address);
    }
}
