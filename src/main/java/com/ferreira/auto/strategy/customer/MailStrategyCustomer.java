package com.ferreira.auto.strategy.customer;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class MailStrategyCustomer implements MailStrategy {

    private final String TYPE = "CUSTOMER";
    private final String LOGO_NAME = "ferrieiraLogo";

    @Autowired
    private Environment environment;

    @Override
    public Context getContextSendMail(MailEvents mailEvents) {

        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("name", mailEvents.getCustomer().getName());
        ctx.setVariable("id", mailEvents.getCustomer().getId());
        ctx.setVariable("ferrieiraLogo", mailEvents.getLogo());

        String frontendUrl = environment.getProperty("FERREIRA_AUTO_URL_FRONT", "");
        ctx.setVariable("frontendUrl", frontendUrl);

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
