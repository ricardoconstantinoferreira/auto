package com.ferreira.auto.publisher.customer;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.customer.MailEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SendMailPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void doSendMail(Customer customer, String templateName, String logo, String subject) {
        MailEvents mailEvents = new MailEvents(this, customer, templateName, subject, logo);
        eventPublisher.publishEvent(mailEvents);
    }
}
