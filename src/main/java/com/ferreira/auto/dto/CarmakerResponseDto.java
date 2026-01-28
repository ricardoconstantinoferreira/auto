package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Carmaker;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record CarmakerResponseDto(String message, String code, @NotBlank Optional<Carmaker> carmaker) {
}
