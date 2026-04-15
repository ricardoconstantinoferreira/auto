package com.ferreira.auto.infra.validator;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.exception.OrderWithoutStockException;
import com.ferreira.auto.exception.PartialOrderPlacedException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @Mock
    private OrderService orderService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private OrderValidator validator;

    @Test
    void validatorThrowsWhenNoStock() {
        OrderDto dto = new OrderDto();

        when(orderService.validateItemsQtdeOrders(dto.getItemsDto())).thenReturn(Map.of(
                "with_qty", List.of(),
                "without_qty", List.of()
        ));

        when(messageInternationalization.getMessage("order.no.stock")).thenReturn("no stock");
        when(messageInternationalization.getMessage("order.no.stock.description")).thenReturn("desc");

        assertThrows(OrderWithoutStockException.class, () -> validator.validator(dto));
    }

    @Test
    void validatorThrowsWhenPartialStock() {
        OrderDto dto = new OrderDto();

        StockInterface with = org.mockito.Mockito.mock(StockInterface.class);
        StockInterface without = org.mockito.Mockito.mock(StockInterface.class);

        when(orderService.validateItemsQtdeOrders(dto.getItemsDto())).thenReturn(Map.of(
                "with_qty", new ArrayList<>(List.of(with)),
                "without_qty", new ArrayList<>(List.of(without))
        ));

        when(orderService.getItemsNoEmpty(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(dto);

        Order order = new Order();
        when(orderService.save(dto)).thenReturn(order);

        when(messageInternationalization.getMessage("order.add.done")).thenReturn("done");
        when(messageInternationalization.getMessage("subject.order.message")).thenReturn("subject");
        when(messageInternationalization.getMessage("order.partial.stock")).thenReturn("partial");
        when(messageInternationalization.getMessage("order.partial.stock.description")).thenReturn("partial desc");

        // validator should save and then throw PartialOrderPlacedException
        assertThrows(PartialOrderPlacedException.class, () -> validator.validator(dto));
    }

    @Test
    void validatorReturnsWhenAllInStock() {
        OrderDto dto = new OrderDto();

        StockInterface with = org.mockito.Mockito.mock(StockInterface.class);

        when(orderService.validateItemsQtdeOrders(dto.getItemsDto())).thenReturn(Map.of(
                "with_qty", new ArrayList<>(List.of(with)),
                "without_qty", new ArrayList<>()
        ));

        Order order = new Order();
        when(orderService.save(dto)).thenReturn(order);

        when(messageInternationalization.getMessage("order.add.done")).thenReturn("done");
        when(messageInternationalization.getMessage("subject.order.message")).thenReturn("subject");

        OrderResponseDto response = validator.validator(dto);

        assertEquals("done", response.message());
        verify(orderService).sendMailOrder(org.mockito.ArgumentMatchers.eq(order.getCustomer()), org.mockito.ArgumentMatchers.eq("subject"), org.mockito.ArgumentMatchers.eq(order));
    }
}
