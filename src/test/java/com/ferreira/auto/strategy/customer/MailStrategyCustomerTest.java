package com.ferreira.auto.strategy.customer;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.mail.MailEvents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.env.Environment;
import org.thymeleaf.context.Context;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailStrategyCustomerTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private MailStrategyCustomer strategy;

    @Test
    void getContextSendMailFillsVariables() {
        // garantir que a variável de ambiente seja fornecida pelo mock para não causar NPE
        when(environment.getProperty("FERREIRA_AUTO_URL_FRONT", "")).thenReturn("http://localhost:4200");

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
        assertEquals("http://localhost:4200", context.getVariable("frontendUrl"));
        verify(environment).getProperty("FERREIRA_AUTO_URL_FRONT", "");
    }

    @Test
    void logoAndTypeAreStable() {
        assertEquals("ferrieiraLogo", strategy.getLogo());
        assertEquals("CUSTOMER", strategy.getType());
    }
}
