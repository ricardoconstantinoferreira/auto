package com.ferreira.auto.publisher.customer;

import com.ferreira.auto.entity.mail.MailEvents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendMailPublisherTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private SendMailPublisher publisher;

    @Test
    void doSendMailPublishesEvent() {
        MailEvents event = new MailEvents(this);

        publisher.doSendMail(event);

        verify(eventPublisher).publishEvent(event);
    }
}
