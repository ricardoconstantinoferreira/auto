package com.ferreira.auto.service;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDto save(List<OrderDto> orderDto);
    List<Order> getByCustomerId(Long customerId);
}
