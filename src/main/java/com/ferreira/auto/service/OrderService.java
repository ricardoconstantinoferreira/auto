package com.ferreira.auto.service;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Order;

public interface OrderService {

    Order sendRabbit(OrderDto orderDto);
    Order save(OrderDto orderDto);
    void sendMailOrder(Customer customer, String subject, Order order);
}
