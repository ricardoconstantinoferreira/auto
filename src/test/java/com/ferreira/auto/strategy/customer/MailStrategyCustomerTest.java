package com.ferreira.auto.strategy.customer;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.mail.MailEvents;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;

class MailStrategyCustomerTest {

    private final MailStrategyCustomer strategy = new MailStrategyCustomer();

    @Test
    void getContextSendMailFillsVariables() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Ana");
        MailEvents event = new MailEvents(this);
        event.setCustomer(customer);
        event.setLogo("logo-path");

        Context context = strategy.getContextSendMail(event);

        assertEquals("Ana", context.getVariable("name"));
        assertEquals(1L, context.getVariable("id"));
        assertEquals("logo-path", context.getVariable("ferrieiraLogo"));
    }

    @Test
    void logoAndTypeAreStable() {
        assertEquals("ferrieiraLogo", strategy.getLogo());
        assertEquals("CUSTOMER", strategy.getType());
    }
}
