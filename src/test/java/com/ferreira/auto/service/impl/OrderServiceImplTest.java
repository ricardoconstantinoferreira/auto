package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ItemsDto;
import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.publisher.customer.SendMailPublisher;
import com.ferreira.auto.repository.OrderItemsRepository;
import com.ferreira.auto.repository.OrderRepository;
import com.ferreira.auto.service.CustomerService;
import com.ferreira.auto.service.ModelService;
import com.ferreira.auto.service.PrerentService;
import com.ferreira.auto.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelService modelService;

    @Mock
    private PrerentService prerentService;

    @Mock
    private StockService stockService;

    @Mock
    private SendMailPublisher sendMail;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void saveCreatesOrderItemsUpdatesPriceAndChangesStatus() {
        Customer customer = new Customer();
        customer.setId(5L);
        OrderDto dto = new OrderDto();
        dto.setCustomerId(5L);
        ItemsDto item1 = new ItemsDto();
        item1.setModelId(1L);
        ItemsDto item2 = new ItemsDto();
        item2.setModelId(2L);
        dto.setItemsDto(List.of(item1, item2));

        Model model1 = new Model();
        model1.setPrice(10f);
        Model model2 = new Model();
        model2.setPrice(15f);
        Order responseOrder = new Order();
        responseOrder.setId(11L);
        Order totalOrder = new Order();
        totalOrder.setId(11L);

        when(customerService.getById(5L)).thenReturn(customer);
        when(modelService.getById(1L)).thenReturn(model1);
        when(modelService.getById(2L)).thenReturn(model2);
        when(orderRepository.save(any(Order.class))).thenReturn(responseOrder, totalOrder);
        when(orderRepository.findById(11L)).thenReturn(Optional.of(totalOrder));

        Order result = service.save(dto);

        assertSame(responseOrder, result);
        verify(orderItemsRepository, times(2)).save(any());
        verify(prerentService).changedStatus(5L);
        assertEquals(25f, totalOrder.getTotalPrice());
    }

    @Test
    void saveThrowsRuntimeWhenDependencyFails() {
        OrderDto dto = new OrderDto();
        dto.setCustomerId(9L);
        when(customerService.getById(9L)).thenThrow(new RuntimeException("boom"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.save(dto));
        assertEquals("boom", ex.getMessage());
    }

    @Test
    void sendMailOrderBuildsEvent() {
        Customer customer = new Customer();
        Order order = new Order();
        order.setId(99L);

        service.sendMailOrder(customer, "subject", order);

        ArgumentCaptor<MailEvents> captor = ArgumentCaptor.forClass(MailEvents.class);
        verify(sendMail).doSendMail(captor.capture());
        MailEvents event = captor.getValue();
        assertEquals("mailStrategyOrder", event.getType());
        assertEquals(99L, event.getOrderId());
    }
}
