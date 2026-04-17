package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.entity.lib.ValueTotalInterface;
import com.ferreira.auto.repository.ExpensesRepository;
import com.ferreira.auto.repository.OrderRepository;
import com.ferreira.auto.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Override
    public List<ModelGraphicInterface> findQtdeModelByPeriod(String month, String year) {
        return orderRepository.findQtdeModelByPeriod(month, year);
    }

    @Override
    public BigDecimal findValueTotalByPeriod(String month, String year) {
        Float value = Float.valueOf(0);
        Float expenses = Float.valueOf(0);
        ValueTotalInterface valueTotal = orderRepository.findValueTotalByPeriod(month, year);
        ValueTotalInterface valueExpenses = expensesRepository.findValueTotalByPeriodExpenses(month, year);

        if (valueTotal != null) {
            value = (!valueTotal.getTotalValue().toString().isEmpty()) ? valueTotal.getTotalValue() : 0;
        }

        if (valueExpenses != null) {
            expenses = (!valueExpenses.getTotalValue().toString().isEmpty()) ? valueExpenses.getTotalValue() : 0;
        }

        Float result = value - expenses;

        BigDecimal valueResult = new BigDecimal(result.toString());
        return valueResult.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public List<CustomerGraphicInterface> findQtdeCustomerByPeriod(String month, String year) {
        return orderRepository.findQtdeCustomerByPeriod(month, year);
    }
}
