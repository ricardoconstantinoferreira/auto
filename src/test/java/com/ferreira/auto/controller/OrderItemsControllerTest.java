package com.ferreira.auto.controller;

import com.ferreira.auto.entity.OrderItems;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemsControllerTest {

    @Mock
    private OrderItemsService orderItemsService;

    @InjectMocks
    private OrderItemsController controller;

    @Test
    void getByCustomerIdReturnsOk() {
        when(orderItemsService.getByCustomerId(3L)).thenReturn(List.of(new OrderItems()));

        ResponseEntity<List<OrderItems>> response = controller.getByCustomerId(3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByOrderIdReturnsOk() {
        when(orderItemsService.getByOrderId(4L)).thenReturn(List.of(new OrderItems()));

        ResponseEntity<List<OrderItems>> response = controller.getByOrderId(4L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
