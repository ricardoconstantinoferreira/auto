package com.ferreira.auto.service;

import com.ferreira.auto.entity.OrderItems;

import java.util.List;

public interface OrderItemsService {

    List<OrderItems> getByCustomerId(Long customerId);

    List<OrderItems> getByOrderId(Long orderId);
}
