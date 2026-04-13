package com.ferreira.auto.controller;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping(value = "api/auto/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/model-by-period/{month}/{year}")
    public ResponseEntity<List<ModelGraphicInterface[]>> findQtdeModelByPeriod(@PathVariable(value = "month") String month,
                                                                               @PathVariable(value = "year") String year) {
        List<ModelGraphicInterface[]> model = dashboardService.findQtdeModelByPeriod(month, year);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping("/order-value-total/{month}/{year}")
    public ResponseEntity<BigDecimal> findValueTotalByPeriod(@PathVariable(value = "month") String month,
                                                        @PathVariable(value = "year") String year) {
        BigDecimal valueTotal = dashboardService.findValueTotalByPeriod(month, year);
        BigDecimal scaledValueTotal = (valueTotal != null ? valueTotal : BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);

        return ResponseEntity.status(HttpStatus.OK).body(scaledValueTotal);
    }

    @GetMapping("/customer-by-period/{month}/{year}")
    public ResponseEntity<List<CustomerGraphicInterface[]>> findQtdeCustomerByPeriod(@PathVariable(value = "month") String month,
                                                                               @PathVariable(value = "year") String year) {
        List<CustomerGraphicInterface[]> customer = dashboardService.findQtdeCustomerByPeriod(month, year);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
