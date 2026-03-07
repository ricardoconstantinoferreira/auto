package com.ferreira.auto.listener.customer;

import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.service.EmailService;
import com.ferreira.auto.strategy.MailStrategy;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendMailListenerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private MailStrategy mailStrategy;

    @Test
    void constructorBuildsStrategyMap() {
        when(mailStrategy.getType()).thenReturn("ORDER");

        SendMailListener listener = new SendMailListener(List.of(mailStrategy));
        Map<?, ?> map = (Map<?, ?>) ReflectionTestUtils.getField(listener, "mailStrategys");

        assertNotNull(map);
        assertEquals(1, map.size());
    }

    @Test
    void onApplicationEventDelegatesToEmailService() throws Exception {
        when(mailStrategy.getType()).thenReturn("ORDER");
        SendMailListener listener = new SendMailListener(List.of(mailStrategy));
        ReflectionTestUtils.setField(listener, "emailService", emailService);

        MailEvents event = new MailEvents(this);
        event.setType("ORDER");

        listener.onApplicationEvent(event);

        verify(emailService).sendEmail(event, mailStrategy);
    }

    @Test
    void onApplicationEventWrapsMessagingException() throws Exception {
        when(mailStrategy.getType()).thenReturn("ORDER");
        SendMailListener listener = new SendMailListener(List.of(mailStrategy));
        ReflectionTestUtils.setField(listener, "emailService", emailService);
        doThrow(new MessagingException("x")).when(emailService).sendEmail(any(), any());

        MailEvents event = new MailEvents(this);
        event.setType("ORDER");

        assertThrows(RuntimeException.class, () -> listener.onApplicationEvent(event));
    }

    @Test
    void onApplicationEventWrapsEncodingException() throws Exception {
        when(mailStrategy.getType()).thenReturn("ORDER");
        SendMailListener listener = new SendMailListener(List.of(mailStrategy));
        ReflectionTestUtils.setField(listener, "emailService", emailService);
        doThrow(new UnsupportedEncodingException("x")).when(emailService).sendEmail(any(), any());

        MailEvents event = new MailEvents(this);
        event.setType("ORDER");

        assertThrows(RuntimeException.class, () -> listener.onApplicationEvent(event));
    }

    @Test
    void supportsAsyncExecutionDelegatesToDefault() {
        when(mailStrategy.getType()).thenReturn("ORDER");
        SendMailListener listener = new SendMailListener(List.of(mailStrategy));

        assertTrue(listener.supportsAsyncExecution());
    }
}
