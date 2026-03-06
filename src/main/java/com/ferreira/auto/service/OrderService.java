package com.ferreira.auto.service;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.Order;

import java.util.List;

public interface OrderService {

    void sendRabbit(OrderDto orderDto);
    void save(OrderDto orderDto);
    List<Order> getByCustomerId(Long customerId);
}
