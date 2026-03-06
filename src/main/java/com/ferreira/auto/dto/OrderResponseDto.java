package com.ferreira.auto.dto;

import com.ferreira.auto.entity.Order;

public record OrderResponseDto(String message, String code, Order order) {
}
