package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ItemsDto;
import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.*;
import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.infra.configuration.RabbitMQConfig;
import com.ferreira.auto.publisher.customer.SendMailPublisher;
import com.ferreira.auto.repository.OrderItemsRepository;
import com.ferreira.auto.repository.OrderRepository;
import com.ferreira.auto.service.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private CustomerService customerService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PrerentService prerentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SendMailPublisher sendMail;

    @Override
    public Order sendRabbit(OrderDto orderDto) {
        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.EXCHANGE_ORDER,
                RabbitMQConfig.ROUTING_KEY,
                orderDto);

        if (response instanceof Order order) {
            return order;
        }

        throw new RuntimeException("Order response from queue is invalid or null");
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER)
    public Order consumeRabbit(OrderDto orderDto) {
        return save(orderDto);
    }

    @Override
    public Order save(OrderDto orderDto) {
        float totalPrice = 0;

        try {
            Order order = new Order();
            Customer customer = customerService.getById(orderDto.getCustomerId());

            order.setCustomer(customer);
            order.setDateOrder(LocalDateTime.now());
            order.setTotalPrice(0);

            Order responseOrder = orderRepository.save(order);

            if (responseOrder.getId() != null) {
                for (ItemsDto dto: orderDto.getItemsDto()) {

                    Model model = modelService.getById(dto.getModelId());
                    OrderItems orderItems = new OrderItems();
                    orderItems.setCustomer(customer);
                    orderItems.setOrder(responseOrder);
                    orderItems.setModel(model);
                    orderItems.setPrice(model.getPrice());
                    totalPrice += model.getPrice();

                    orderItemsRepository.save(orderItems);
                    stockService.updateQtdeByModelId(model.getId());
                }

                updatePriceTotalOrder(responseOrder.getId(), totalPrice);
                prerentService.changedStatus(customer.getId());
            }

            return responseOrder;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Order> getOrderByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public void sendMailOrder(Customer customer, String subject, Order order) {
        String templateName = "order";
        String logo = "templates/images/ferrieira-auto.png";

        MailEvents mailEvents = new MailEvents(this);
        mailEvents.setCustomer(customer);
        mailEvents.setSubject(subject);
        mailEvents.setTemplateName(templateName);
        mailEvents.setLogo(logo);
        mailEvents.setOrderId(order.getId());
        mailEvents.setType("mailStrategyOrder");

        sendMail.doSendMail(mailEvents);
    }

    private void updatePriceTotalOrder(Long id, float price) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            Order order = orderOptional.get();
            order.setTotalPrice(price);

            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
