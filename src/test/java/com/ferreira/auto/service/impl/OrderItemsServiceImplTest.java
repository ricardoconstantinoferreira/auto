package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.OrderItems;
import com.ferreira.auto.repository.OrderItemsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemsServiceImplTest {

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderItemsServiceImpl service;

    @Test
    void getByCustomerIdDelegates() {
        when(orderItemsRepository.findByOrderByCustomerId(1L)).thenReturn(List.of(new OrderItems()));
        assertEquals(1, service.getByCustomerId(1L).size());
    }

    @Test
    void getByOrderIdDelegates() {
        when(orderItemsRepository.findByOrderByOrderId(2L)).thenReturn(List.of(new OrderItems()));
        assertEquals(1, service.getByOrderId(2L).size());
    }
}
