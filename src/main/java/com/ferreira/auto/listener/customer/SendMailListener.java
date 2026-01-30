package com.ferreira.auto.listener.customer;

import com.ferreira.auto.entity.customer.MailEvents;
import com.ferreira.auto.service.EmailService;
import com.ferreira.auto.strategy.MailStrategy;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class SendMailListener implements ApplicationListener<MailEvents> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MailStrategy mailStrategy;

    @Override
    public void onApplicationEvent(MailEvents event) {
        try {
            emailService.sendEmail(event, mailStrategy);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
