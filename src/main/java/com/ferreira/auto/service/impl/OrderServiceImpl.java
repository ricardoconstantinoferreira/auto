package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.*;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.repository.OrderItemsRepository;
import com.ferreira.auto.repository.OrderRepository;
import com.ferreira.auto.service.CustomerService;
import com.ferreira.auto.service.ModelService;
import com.ferreira.auto.service.OrderService;
import com.ferreira.auto.service.PrerentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private PrerentService prerentService;

    @Override
    public OrderResponseDto save(List<OrderDto> orderDto) {
        OrderResponseDto response = null;
        float totalPrice = 0;

        try {
            Order order = new Order();
            Customer customer = customerService.getById(orderDto.getFirst().getCustomerId());

            order.setCustomer(customer);
            order.setDateOrder(LocalDateTime.now());
            order.setTotalPrice(0);

            Order responseOrder = orderRepository.save(order);

            if (responseOrder.getId() != null) {
                for (OrderDto dto: orderDto) {

                    Model model = modelService.getById(dto.getModelId());
                    OrderItems orderItems = new OrderItems();
                    orderItems.setCustomer(customer);
                    orderItems.setOrder(responseOrder);
                    orderItems.setModel(model);
                    orderItems.setPrice(model.getPrice());
                    totalPrice += model.getPrice();

                    orderItemsRepository.save(orderItems);
                }

                updatePriceTotalOrder(responseOrder.getId(), totalPrice);
                prerentService.changedStatus(customer.getId());

                Optional<Order> orderOptional = Optional.ofNullable(responseOrder);
                response = new OrderResponseDto(
                        messageInternationalization.getMessage("order.add.done"),
                        "200",
                        orderOptional);
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void updatePriceTotalOrder(Long id, float price) {
        try {
            Order order = orderRepository.getReferenceById(id);
            order.setTotalPrice(price);

            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Order> getByCustomerId(Long customerId) {
        return List.of();
    }
}
