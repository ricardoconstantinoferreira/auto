package com.ferreira.auto.infra.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageInternationalizationTest {

    @Test
    void getMessageUsesLocaleContextHolder() {
        MessageSource messageSource = mock(MessageSource.class);
        MessageInternationalization messageInternationalization = new MessageInternationalization();
        ReflectionTestUtils.setField(messageInternationalization, "messageSource", messageSource);
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        when(messageSource.getMessage("code", null, Locale.ENGLISH)).thenReturn("translated");

        String message = messageInternationalization.getMessage("code");

        assertEquals("translated", message);
    }
}
