package com.ferreira.auto.publisher.customer;

import com.ferreira.auto.entity.mail.MailEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SendMailPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void doSendMail(MailEvents mailEvents) {
        eventPublisher.publishEvent(mailEvents);
    }
}
