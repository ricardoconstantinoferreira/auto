package com.ferreira.auto.controller;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.OrderInterface;
import com.ferreira.auto.infra.validator.OrderValidator;
import com.ferreira.auto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auto/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderValidator orderValidator;

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderDto orderDto) {
        OrderResponseDto responseDto = orderValidator.validator(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return new ResponseEntity<>(orderService.getOrderByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("/list-rent/{status}")
    public ResponseEntity<List<OrderInterface>> getListOrderRent(@PathVariable(value = "status") Long status) {
        List<OrderInterface> list = orderService.findByListOrderRent(status);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/returned/{orderId}")
    public ResponseEntity<Order> getReturnedValuesModels(@PathVariable(value = "orderId") Long orderId) {
        Order order = orderService.getReturnedValuesModels(orderId);
        orderService.addQtyReturnModel(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
