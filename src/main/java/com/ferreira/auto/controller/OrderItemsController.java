package com.ferreira.auto.controller;

import com.ferreira.auto.entity.lib.OrderItemsInterface;
import com.ferreira.auto.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/auto/order-items")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderItemsService;

    @GetMapping("/order/customer/{orderId}/{customerId}")
    public ResponseEntity<List<OrderItemsInterface>> getByOrderItemsByOrderIdAndCustomerId(@PathVariable(value = "orderId") Long orderId,
                                                                                           @PathVariable(value = "customerId") Long customerId) {
        return new ResponseEntity<>(orderItemsService.getByOrderItemsByOrderIdAndCustomerId(orderId, customerId), HttpStatus.OK);
    }
}
