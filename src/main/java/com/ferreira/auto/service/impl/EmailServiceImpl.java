package com.ferreira.auto.service.impl;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.service.EmailService;
import com.ferreira.auto.strategy.MailStrategy;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collections;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${resend.api.key}")
    private String apiKey;

    @Override
    public void sendEmail(MailEvents mailEvents, MailStrategy mailStrategy) throws MessagingException, IOException, ResendException {

        Resend resend = new Resend(apiKey);

        String mailFrom = environment.getProperty("resend.domain");
        String logo = mailEvents.getLogo();

        byte[] imageBytes = new ClassPathResource(logo).getContentAsByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        Attachment logoAnexo = Attachment.builder()
                .fileName(logo)
                .content(base64Image)
                .contentId("ferrieiraLogo")
                .build();

        final Context ctx = mailStrategy.getContextSendMail(mailEvents);
        final String htmlContent = templateEngine.process(mailEvents.getTemplateName(), ctx);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(mailFrom)
                .to(mailEvents.getCustomer().getEmail())
                .subject(mailEvents.getSubject())
                .html(htmlContent)
                .attachments(Collections.singletonList(logoAnexo))
                .build();

        resend.emails().send(params);
    }
}
