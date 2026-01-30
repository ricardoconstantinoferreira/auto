package com.ferreira.auto.strategy.customer;

import com.ferreira.auto.entity.customer.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class MailStrategyCustomer implements MailStrategy {

    private final String LOGO_NAME = "ferrieiraLogo";

    @Override
    public Context getContextSendMail(MailEvents mailEvents) {

        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("name", mailEvents.getCustomer().getName());
        ctx.setVariable("id", mailEvents.getCustomer().getId());
        ctx.setVariable("ferrieiraLogo", mailEvents.getLogo());

        return ctx;
    }

    @Override
    public String getLogo() {
        return LOGO_NAME;
    }
}
