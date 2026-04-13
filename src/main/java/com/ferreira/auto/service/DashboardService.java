package com.ferreira.auto.service;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardService {

    List<ModelGraphicInterface[]> findQtdeModelByPeriod(String month, String year);

    BigDecimal findValueTotalByPeriod(String month, String year);

    List<CustomerGraphicInterface[]> findQtdeCustomerByPeriod(String month, String year);
}
