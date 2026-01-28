package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Address;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record AddressResponseDto(String message, String code, @NotBlank Optional<Address> address) {
}
