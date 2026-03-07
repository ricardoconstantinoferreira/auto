package com.ferreira.auto.strategy.order;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.mail.MailEvents;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;

class MailStrategyOrderTest {

    private final MailStrategyOrder strategy = new MailStrategyOrder();

    @Test
    void getContextSendMailFillsVariables() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("Bob");
        MailEvents event = new MailEvents(this);
        event.setCustomer(customer);
        event.setLogo("logo-path");
        event.setOrderId(44L);

        Context context = strategy.getContextSendMail(event);

        assertEquals("Bob", context.getVariable("name"));
        assertEquals(2L, context.getVariable("id"));
        assertEquals("logo-path", context.getVariable("ferrieiraLogo"));
        assertEquals(44L, context.getVariable("order_id"));
    }

    @Test
    void logoAndTypeAreStable() {
        assertEquals("ferrieiraLogo", strategy.getLogo());
        assertEquals("ORDER", strategy.getType());
    }
}
