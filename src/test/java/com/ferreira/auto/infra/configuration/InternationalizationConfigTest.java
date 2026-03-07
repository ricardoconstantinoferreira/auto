package com.ferreira.auto.infra.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InternationalizationConfigTest {

    private final InternationalizationConfig config = new InternationalizationConfig();

    @Test
    void resolveLocaleReturnsDefaultWhenHeaderMissing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Accept-Language")).thenReturn(null);

        Locale locale = config.resolveLocale(request);

        assertEquals(Locale.getDefault(), locale);
    }

    @Test
    void resolveLocaleReturnsLookupWhenHeaderPresent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Accept-Language")).thenReturn("en-US,en;q=0.9");

        Locale locale = config.resolveLocale(request);

        assertEquals(Locale.forLanguageTag("en"), locale);
    }

    @Test
    void messageSourceConfigured() {
        ResourceBundleMessageSource source = config.messageSource();

        assertEquals("messages", source.getBasenameSet().iterator().next());
    }
}
