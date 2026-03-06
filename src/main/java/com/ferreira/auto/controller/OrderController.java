package com.ferreira.auto.controller;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auto/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderDto orderDto) {
        Order order = orderService.sendRabbit(orderDto);

        OrderResponseDto responseDto = new OrderResponseDto(
                messageInternationalization.getMessage("order.add.done"),
                "201",
                order);

        orderService.sendMailOrder(order.getCustomer(), messageInternationalization.getMessage("subject.order.message"),
                order);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
