package com.ferreira.auto.controller;

import com.ferreira.auto.entity.lib.OrderItemsInterface;
import com.ferreira.auto.service.OrderItemsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemsControllerTest {

    @Mock
    private OrderItemsService orderItemsService;

    @InjectMocks
    private OrderItemsController controller;

    @Test
    void getByOrderItemsByOrderIdAndCustomerIdReturnsOk() {
        OrderItemsInterface oi = mock(OrderItemsInterface.class);
        when(orderItemsService.getByOrderItemsByOrderIdAndCustomerId(5L, 3L)).thenReturn(List.of(oi));

        ResponseEntity<List<OrderItemsInterface>> response = controller.getByOrderItemsByOrderIdAndCustomerId(5L, 3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
