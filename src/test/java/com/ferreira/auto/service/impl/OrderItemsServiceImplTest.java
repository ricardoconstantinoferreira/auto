package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.OrderItemsInterface;
import com.ferreira.auto.repository.OrderItemsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemsServiceImplTest {

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderItemsServiceImpl service;

    @Test
    void getByOrderItemsByOrderIdAndCustomerIdReturnsList() {
        OrderItemsInterface oi = mock(OrderItemsInterface.class);
        when(orderItemsRepository.findByOrderItemsByOrderIdAndCustomerId(5L, 3L)).thenReturn(List.of(oi));

        List<OrderItemsInterface> result = service.getByOrderItemsByOrderIdAndCustomerId(5L, 3L);

        assertEquals(1, result.size());
        assertSame(oi, result.get(0));
        verify(orderItemsRepository).findByOrderItemsByOrderIdAndCustomerId(5L, 3L);
    }

    @Test
    void returnsEmptyWhenNoneFound() {
        when(orderItemsRepository.findByOrderItemsByOrderIdAndCustomerId(7L, 8L)).thenReturn(Collections.emptyList());

        List<OrderItemsInterface> result = service.getByOrderItemsByOrderIdAndCustomerId(7L, 8L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderItemsRepository).findByOrderItemsByOrderIdAndCustomerId(7L, 8L);
    }
}
