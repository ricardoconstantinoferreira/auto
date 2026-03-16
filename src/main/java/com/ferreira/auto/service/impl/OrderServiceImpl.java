package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ItemsDto;
import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.*;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.entity.lib.OrderInterface;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    @Autowired
    private RentalService rentalService;

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
            order.setStatusOrder(StatusOrder.RENTED);
            order.setInterestValuePayment(0);

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

    @Override
    public Map<String, List<StockInterface>> validateItemsQtdeOrders(List<ItemsDto> itemsDtos) {
        List<StockInterface> itemsWithQtde = new ArrayList<>();
        List<StockInterface> itemsWithoutQtde = new ArrayList<>();
        Map<String, List<StockInterface>> maps = new HashMap<>();

        for (ItemsDto item: itemsDtos) {
            StockInterface stock = stockService.findStockWithModelByModelId(item.getModelId());

            if (stock.getQtde() > 0) {
                itemsWithQtde.add(stock);
            } else {
                itemsWithoutQtde.add(stock);
            }
        }

        maps.put("with_qty", itemsWithQtde);
        maps.put("without_qty", itemsWithoutQtde);
        return maps;
    }

    @Override
    public OrderDto getItemsNoEmpty(OrderDto orderDto, ArrayList<StockInterface> stockWithoutQty) {
        List<ItemsDto> items = orderDto.getItemsDto();
        List<ItemsDto> dtoItems = new ArrayList<>();
        OrderDto orderDto1 = new OrderDto();

        for (ItemsDto item: items) {
            for (StockInterface stock: stockWithoutQty) {
                if (item.getModelId().equals(stock.getId())) {
                    dtoItems.add(item);
                }
            }
        }

        orderDto1.setCustomerId(orderDto.getCustomerId());
        orderDto1.setItemsDto(dtoItems);

        return orderDto1;
    }

    @Override
    public List<OrderInterface> findByListOrderRent(Long status) {
        StatusOrder statusOrder = (status == 0) ? StatusOrder.RENTED : StatusOrder.RETURNED;
        return orderRepository.findByListOrderRent(statusOrder);
    }

    @Override
    public Order getReturnedValuesModels(Long orderId) {
        Rental rental = rentalService.getRental();
        Order order = getById(orderId);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateOrderCount = order.getDateOrder().plusDays(rental.getQtdeDaysRent());

        if (now.isAfter(dateOrderCount)) {
            Long delay = ChronoUnit.DAYS.between(dateOrderCount, now);
            float result = (rental.getPercentageInterest() * order.getTotalPrice()) / 100;
            float interest = result * delay;

            order.setInterestValuePayment(interest);
        }

        order.setStatusOrder(StatusOrder.RETURNED);
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
