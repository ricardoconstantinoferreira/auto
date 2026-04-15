package com.ferreira.auto.controller;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.OrderInterface;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.infra.validator.OrderValidator;
import com.ferreira.auto.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private OrderController controller;

    @Test
    void saveReturnsCreatedAndSendsMail() {
        OrderDto dto = new OrderDto();
        dto.setItemsDto(List.of()); // garantir não-nulo

        Customer customer = new Customer();
        Order order = new Order();
        order.setId(8L);
        order.setCustomer(customer);

        OrderResponseDto responseDto = new OrderResponseDto("done", "201", order);
        when(orderValidator.validator(dto)).thenReturn(responseDto);

        ResponseEntity<OrderResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("done", response.getBody().message());
    }

    @Test
    void getOrderByCustomerIdReturnsOk() {
        Order order = new Order();
        when(orderService.getOrderByCustomerId(5L)).thenReturn(List.of(order));

        ResponseEntity<List<Order>> response = controller.getOrderByCustomerId(5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getListOrderRentReturnsOk() {
        OrderInterface oi = mock(OrderInterface.class);
        when(orderService.findByListOrderRent(0L)).thenReturn(List.of(oi));

        ResponseEntity<List<OrderInterface>> response = controller.getListOrderRent(0L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getReturnedValuesModelsDelegatesAndReturnsOk() {
        Order order = new Order();
        order.setId(10L);
        when(orderService.getReturnedValuesModels(10L)).thenReturn(order);

        ResponseEntity<Order> response = controller.getReturnedValuesModels(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(order, response.getBody());
        verify(orderService).addQtyReturnModel(10L);
    }
}
