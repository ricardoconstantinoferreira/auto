package com.ferreira.auto.controller;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.entity.lib.ValueTotalInterface;
import com.ferreira.auto.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController controller;

    @Test
    void findQtdeModelByPeriodReturnsCreatedWithData() {
        ModelGraphicInterface iface = new ModelGraphicInterface() {
            @Override
            public String getDescription() { return "M1"; }

            @Override
            public Long getQtde() { return 7L; }
        };

        List<ModelGraphicInterface> modelInterface = new ArrayList<>();
        modelInterface.add(iface);

        when(dashboardService.findQtdeModelByPeriod("1", "2024"))
                .thenReturn(modelInterface);

        ResponseEntity<List<ModelGraphicInterface>> response = controller.findQtdeModelByPeriod("1", "2024");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("M1", response.getBody().get(0).getDescription());
    }

    @Test
    void findValueTotalByPeriodReturnsCreatedWithScaledValue() {
        ValueTotalInterface valueTotalInterface = new ValueTotalInterface() {
            @Override
            public Float getTotalValue() {
                return 100.46f;
            }
        };

        when(dashboardService.findValueTotalByPeriod("4", "2023")).thenReturn(valueTotalInterface);

        ResponseEntity<BigDecimal> response = controller.findValueTotalByPeriod("4", "2023");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("100.46"), response.getBody());
    }

    @Test
    void findQtdeCustomerByPeriodReturnsCreatedWithData() {
        CustomerGraphicInterface ci = new CustomerGraphicInterface() {
            @Override
            public String getName() { return "C1"; }

            @Override
            public Long getQtde() { return 3L; }
        };

        List<CustomerGraphicInterface> customerInterface = new ArrayList<>();
        customerInterface.add(ci);

        when(dashboardService.findQtdeCustomerByPeriod("6", "2022"))
            .thenReturn(customerInterface);

        ResponseEntity<List<CustomerGraphicInterface>> response = controller.findQtdeCustomerByPeriod("6", "2022");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("C1", response.getBody().get(0).getName());
    }
}
