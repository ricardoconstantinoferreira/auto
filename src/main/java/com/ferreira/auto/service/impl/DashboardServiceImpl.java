package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.repository.OrderRepository;
import com.ferreira.auto.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<ModelGraphicInterface> findQtdeModelByPeriod(String month, String year) {
        return orderRepository.findQtdeModelByPeriod(month, year);
    }

    @Override
    public BigDecimal findValueTotalByPeriod(String month, String year) {
        return orderRepository.findValueTotalByPeriod(month, year);
    }

    @Override
    public List<CustomerGraphicInterface> findQtdeCustomerByPeriod(String month, String year) {
        return orderRepository.findQtdeCustomerByPeriod(month, year);
    }
}
