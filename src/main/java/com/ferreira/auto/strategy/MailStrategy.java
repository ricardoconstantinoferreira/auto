package com.ferreira.auto.strategy;

import com.ferreira.auto.entity.customer.MailEvents;
import org.thymeleaf.context.Context;

public interface MailStrategy {
    Context getContextSendMail(MailEvents mailEvents);
    String getLogo();
}
