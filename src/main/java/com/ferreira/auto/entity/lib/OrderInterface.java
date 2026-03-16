package com.ferreira.auto.entity.lib;

import com.ferreira.auto.entity.StatusOrder;
import java.time.LocalDateTime;

public interface OrderInterface {
    String getCustomer();
    LocalDateTime getDateOrder();
    Float getTotalPrice();
    StatusOrder getStatusOrder();
    Float getInterestValuePayment();
}
