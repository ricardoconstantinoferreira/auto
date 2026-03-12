package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Category;

public record CategoryResponseDto (String message, String code, Category category) {
}
