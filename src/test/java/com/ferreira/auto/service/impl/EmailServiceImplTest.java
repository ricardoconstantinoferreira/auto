package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private Environment environment;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MailStrategy mailStrategy;

    @InjectMocks
    private EmailServiceImpl service;

    @Test
    void sendEmailBuildsMimeMessageAndSends() throws Exception {
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(environment.getProperty("spring.mail.properties.mail.smtp.from")).thenReturn("from@mail.com");
        when(environment.getProperty("mail.from.name", "Identity")).thenReturn("Auto");
        when(mailStrategy.getContextSendMail(any())).thenReturn(new Context());
        when(mailStrategy.getLogo()).thenReturn("ferrieiraLogo");
        when(templateEngine.process(eq("reset"), any(Context.class))).thenReturn("<html>ok</html>");

        MailEvents event = new MailEvents(this);
        Customer customer = new Customer();
        customer.setEmail("to@mail.com");
        event.setCustomer(customer);
        event.setSubject("subject");
        event.setTemplateName("reset");
        event.setLogo("templates/images/ferrieira-auto.png");

        service.sendEmail(event, mailStrategy);

        verify(mailSender).send(mimeMessage);
    }
}
