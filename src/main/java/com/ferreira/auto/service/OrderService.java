package com.ferreira.auto.service;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Order;

import java.util.List;

public interface OrderService {

    Order sendRabbit(OrderDto orderDto);
    Order save(OrderDto orderDto);
    List<Order> getOrderByCustomerId(Long customerId);
    void sendMailOrder(Customer customer, String subject, Order order);
}
