package com.ferreira.auto.infra.validator;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.exception.OrderWithoutStockException;
import com.ferreira.auto.exception.PartialOrderPlacedException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderValidator {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    public OrderResponseDto validator(OrderDto orderDto) {
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

        Order order = orderService.save(orderDto);
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

        return responseDto;
    }
}
