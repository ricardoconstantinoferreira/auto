package com.ferreira.auto.controller;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private OrderController controller;

    @Test
    void saveReturnsCreatedAndSendsMail() {
        OrderDto dto = new OrderDto();
        Customer customer = new Customer();
        Order order = new Order();
        order.setId(8L);
        order.setCustomer(customer);

        when(orderService.sendRabbit(dto)).thenReturn(order);
        when(messageInternationalization.getMessage("order.add.done")).thenReturn("done");
        when(messageInternationalization.getMessage("subject.order.message")).thenReturn("subject");

        ResponseEntity<OrderResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("done", response.getBody().message());
        verify(orderService).sendMailOrder(customer, "subject", order);
    }
}
