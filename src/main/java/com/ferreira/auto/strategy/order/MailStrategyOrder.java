package com.ferreira.auto.strategy.order;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class MailStrategyOrder implements MailStrategy {

    private final String TYPE = "ORDER";
    private final String LOGO_NAME = "ferrieiraLogo";

    @Override
    public Context getContextSendMail(MailEvents mailEvents) {

        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("name", mailEvents.getCustomer().getName());
        ctx.setVariable("id", mailEvents.getCustomer().getId());
        ctx.setVariable("ferrieiraLogo", mailEvents.getLogo());
        ctx.setVariable("order_id", mailEvents.getOrderId());

        return ctx;
    }

    @Override
    public String getLogo() {
        return LOGO_NAME;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
