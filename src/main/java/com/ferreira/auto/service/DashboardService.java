package com.ferreira.auto.service;

import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.entity.lib.ValueTotalInterface;

import java.util.List;

public interface DashboardService {

    List<ModelGraphicInterface> findQtdeModelByPeriod(String month, String year);

    ValueTotalInterface findValueTotalByPeriod(String month, String year);

    List<CustomerGraphicInterface> findQtdeCustomerByPeriod(String month, String year);
}
