package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Order;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record OrderResponseDto(String message, String code, @NotBlank Optional<Order> order) {
}
