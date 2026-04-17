package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Expenses;

import java.util.Optional;

public record ExpensesResponseDto(String message, String code, Optional<Expenses> expenses) {
}
