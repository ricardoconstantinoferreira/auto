package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    void findQtdeModelByPeriodDelegatesToRepository() {
        ModelGraphicInterface iface = new ModelGraphicInterface() {
            @Override
            public String getDescription() { return "Model A"; }

            @Override
            public Long getQtde() { return 5L; }
        };

        when(orderRepository.findQtdeModelByPeriod("1", "2023")).thenReturn(Collections.singletonList(new ModelGraphicInterface[]{iface}));

        var result = service.findQtdeModelByPeriod("1", "2023");

        assertEquals(1, result.size());
        assertEquals("Model A", result.get(0)[0].getDescription());
        assertEquals(5L, result.get(0)[0].getQtde());
    }

    @Test
    void findValueTotalByPeriodDelegatesToRepository() {
        when(orderRepository.findValueTotalByPeriod("2", "2024")).thenReturn(new BigDecimal("123.45"));

        BigDecimal result = service.findValueTotalByPeriod("2", "2024");

        assertEquals(new BigDecimal("123.45"), result);
    }

    @Test
    void findQtdeCustomerByPeriodDelegatesToRepository() {
        CustomerGraphicInterface ci = new CustomerGraphicInterface() {
            @Override
            public String getName() { return "John"; }

            @Override
            public Long getQtde() { return 2L; }
        };

        when(orderRepository.findQtdeCustomerByPeriod("3", "2025")).thenReturn(Collections.singletonList(new CustomerGraphicInterface[]{ci}));

        var result = service.findQtdeCustomerByPeriod("3", "2025");

        assertEquals(1, result.size());
        assertEquals("John", result.get(0)[0].getName());
        assertEquals(2L, result.get(0)[0].getQtde());
    }
}
