package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Customer;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record CustomerResponseDto(String message, String code, @NotBlank Optional<Customer> customer) {
}
