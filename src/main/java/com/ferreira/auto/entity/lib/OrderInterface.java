package com.ferreira.auto.entity.lib;

import com.ferreira.auto.entity.StatusOrder;
import java.time.LocalDateTime;

public interface OrderInterface {
    Long getId();
    String getCustomer();
    LocalDateTime getDateOrder();
    Float getTotalPrice();
    StatusOrder getStatusOrder();
    Float getInterestValuePayment();
}
