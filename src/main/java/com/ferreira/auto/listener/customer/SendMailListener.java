package com.ferreira.auto.listener.customer;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.service.EmailService;
import com.ferreira.auto.strategy.MailStrategy;
import com.resend.core.exception.ResendException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SendMailListener implements ApplicationListener<MailEvents> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private Map<String, MailStrategy> mailStrategys;

    public SendMailListener(List<MailStrategy> strategyList) {
        this.mailStrategys = strategyList.stream()
                .collect(Collectors.toMap(MailStrategy::getType, Function.identity()));
    }

    @Override
    public void onApplicationEvent(MailEvents event) {
        try {
            MailStrategy mailStrategy = mailStrategys.get(event.getType());
            emailService.sendEmail(event, mailStrategy);
        } catch (ResendException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
