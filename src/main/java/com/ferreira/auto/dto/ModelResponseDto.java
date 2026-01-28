package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Model;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record ModelResponseDto(String message, String code, @NotBlank Optional<Model> model) {
}
