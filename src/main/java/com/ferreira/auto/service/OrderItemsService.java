package com.ferreira.auto.service;

import com.ferreira.auto.entity.lib.OrderItemsInterface;

import java.util.List;

public interface OrderItemsService {
    List<OrderItemsInterface> getByOrderItemsByOrderIdAndCustomerId(Long orderId, Long customerId);
}
