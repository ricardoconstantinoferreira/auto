package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AddressDto;
import com.ferreira.auto.dto.AddressResponseDto;
import com.ferreira.auto.entity.Address;
import com.ferreira.auto.exception.AddressAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<AddressResponseDto> save(@RequestBody AddressDto addressDto) {
        if (addressService.hasAddressByCustomer(addressDto)) {
            throw new AddressAlreadyExistsException(
                    messageInternationalization.getMessage("address.exists.message"),
                    addressDto.getZipcode()
            );
        }

        Address entity = addressService.save(addressDto);
        Optional<Address> optionalAddress = Optional.ofNullable(entity);
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                messageInternationalization.getMessage("address.save.message"), "200", optionalAddress
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> update(@PathVariable(value = "id") Long id,
                                                     @RequestBody AddressDto addressDto) {

        addressDto.setId(id);
        Address entity = addressService.save(addressDto);
        Optional<Address> optionalAddress = Optional.ofNullable(entity);
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                messageInternationalization.getMessage("address.updated.message"), "200", optionalAddress
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(addressService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        return new ResponseEntity<>(addressService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        addressService.delete(id);
    }

}
