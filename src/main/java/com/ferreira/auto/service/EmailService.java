package com.ferreira.auto.service;

import com.ferreira.auto.entity.customer.MailEvents;
import com.ferreira.auto.strategy.MailStrategy;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendEmail(MailEvents mailEvents, MailStrategy mailStrategy) throws MessagingException, UnsupportedEncodingException;
}
