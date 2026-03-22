package com.ferreira.auto.service;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import com.resend.core.exception.ResendException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendEmail(MailEvents mailEvents, MailStrategy mailStrategy) throws MessagingException, IOException, ResendException;
}
