package com.ferreira.auto.controller;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.exception.OrderWithoutStockException;
import com.ferreira.auto.exception.PartialOrderPlacedException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/auto/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderDto orderDto) {

        Map<String, List<StockInterface>> stockDetails = orderService.validateItemsQtdeOrders(orderDto.getItemsDto());

        if (stockDetails.get("with_qty").isEmpty()) {
            throw new OrderWithoutStockException(
                    messageInternationalization.getMessage("order.no.stock"),
                    messageInternationalization.getMessage("order.no.stock.description")
            );
        }

        if (!stockDetails.get("with_qty").isEmpty() && !stockDetails.get("without_qty").isEmpty()) {
            orderDto = orderService.getItemsNoEmpty(orderDto, (ArrayList<StockInterface>) stockDetails.get("with_qty"));
        }

        Order order = orderService.sendRabbit(orderDto);
        OrderResponseDto responseDto = new OrderResponseDto(
                messageInternationalization.getMessage("order.add.done"),
                "201",
                order);

        orderService.sendMailOrder(order.getCustomer(), messageInternationalization.getMessage("subject.order.message"),
                order);

        if (!stockDetails.get("with_qty").isEmpty() && !stockDetails.get("without_qty").isEmpty()) {
            throw new PartialOrderPlacedException(
                    messageInternationalization.getMessage("order.partial.stock"),
                    messageInternationalization.getMessage("order.partial.stock.description")
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return new ResponseEntity<>(orderService.getOrderByCustomerId(customerId), HttpStatus.OK);
    }
}
