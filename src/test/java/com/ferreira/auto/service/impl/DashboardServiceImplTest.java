package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.entity.lib.ValueTotalInterface;
import com.ferreira.auto.repository.ExpensesRepository;
import com.ferreira.auto.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ExpensesRepository expensesRepository;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    void findQtdeModelByPeriodDelegatesToRepository() {
        ModelGraphicInterface iface = new ModelGraphicInterface() {
            @Override
            public String getDescription() { return "M1"; }

            @Override
            public Long getQtde() { return 7L; }
        };

        when(orderRepository.findQtdeModelByPeriod("1", "2024")).thenReturn(List.of(iface));

        var result = service.findQtdeModelByPeriod("1", "2024");

        assertEquals(1, result.size());
        assertEquals("M1", result.get(0).getDescription());
    }

    @Test
    void findValueTotalByPeriodDelegatesToRepository() {
        ValueTotalInterface v = new ValueTotalInterface() {
            @Override
            public Float getTotalValue() { return 10.0f; }
        };

        ValueTotalInterface v1 = new ValueTotalInterface() {
            @Override
            public Float getTotalValue() { return 3.0f; }
        };

        BigDecimal resultDecimal = new BigDecimal("7.00");

        when(orderRepository.findValueTotalByPeriod("2", "2025")).thenReturn(v);
        when(expensesRepository.findValueTotalByPeriodExpenses("2", "2025")).thenReturn(v1);

        BigDecimal result = service.findValueTotalByPeriod("2", "2025");

        assertEquals(resultDecimal, result);
    }

    @Test
    void findQtdeCustomerByPeriodDelegatesToRepository() {
        CustomerGraphicInterface c = new CustomerGraphicInterface() {
            @Override
            public String getName() { return "C1"; }

            @Override
            public Long getQtde() { return 3L; }
        };

        when(orderRepository.findQtdeCustomerByPeriod("3", "2026")).thenReturn(List.of(c));

        var result = service.findQtdeCustomerByPeriod("3", "2026");

        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getName());
    }
}
