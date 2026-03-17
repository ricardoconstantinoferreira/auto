package com.ferreira.auto.service;

import com.ferreira.auto.dto.ItemsDto;
import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.entity.lib.OrderInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Order sendRabbit(OrderDto orderDto);
    Order save(OrderDto orderDto);
    List<Order> getOrderByCustomerId(Long customerId);
    void sendMailOrder(Customer customer, String subject, Order order);
    Map<String, List<StockInterface>> validateItemsQtdeOrders(List<ItemsDto> itemsDtos);
    OrderDto getItemsNoEmpty(OrderDto orderDto, ArrayList<StockInterface> stockWithoutQty);
    List<OrderInterface> findByListOrderRent(Long status);
    Order getReturnedValuesModels(Long orderId);
    Order getById(Long orderId);
    void addQtyReturnModel(Long orderId);
}
