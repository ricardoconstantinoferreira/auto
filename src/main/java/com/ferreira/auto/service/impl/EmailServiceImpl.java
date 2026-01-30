package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.customer.MailEvents;
import com.ferreira.auto.service.EmailService;
import com.ferreira.auto.strategy.MailStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(MailEvents mailEvents, MailStrategy mailStrategy) throws MessagingException, UnsupportedEncodingException {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
        String mailFromName = environment.getProperty("mail.from.name", "Identity");

        email.setTo(mailEvents.getCustomer().getEmail());
        email.setSubject(mailEvents.getSubject());
        email.setFrom(new InternetAddress(mailFrom, mailFromName));

        final Context ctx = mailStrategy.getContextSendMail(mailEvents);
        final String htmlContent = templateEngine.process(mailEvents.getTemplateName(), ctx);

        email.setText(htmlContent, true);
        String logo = mailStrategy.getLogo();

        ClassPathResource clr = new ClassPathResource(mailEvents.getLogo());
        email.addInline(logo, clr, "image/png");

        mailSender.send(mimeMessage);
    }
}
