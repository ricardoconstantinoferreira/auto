package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.OrderItemsInterface;
import com.ferreira.auto.repository.OrderItemsRepository;
import com.ferreira.auto.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public List<OrderItemsInterface> getByOrderItemsByOrderIdAndCustomerId(Long orderId, Long customerId) {
        return orderItemsRepository.findByOrderItemsByOrderIdAndCustomerId(orderId, customerId);
    }
}
