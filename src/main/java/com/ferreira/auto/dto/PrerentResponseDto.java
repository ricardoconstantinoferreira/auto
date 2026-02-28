package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Prerent;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record PrerentResponseDto(String message, String code, @NotBlank Optional<Prerent> prerent) {
}
