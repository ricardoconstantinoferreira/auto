package com.ferreira.auto.infra.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void queueBeanConfigured() {
        Queue queue = config.queue();
        assertEquals(RabbitMQConfig.QUEUE_ORDER, queue.getName());
    }

    @Test
    void exchangeBeanConfigured() {
        DirectExchange exchange = config.exchange();
        assertEquals(RabbitMQConfig.EXCHANGE_ORDER, exchange.getName());
    }

    @Test
    void bindingBeanConfigured() {
        Queue queue = config.queue();
        DirectExchange exchange = config.exchange();

        Binding binding = config.binding(queue, exchange);

        assertEquals(RabbitMQConfig.ROUTING_KEY, binding.getRoutingKey());
    }

    @Test
    void jsonConverterBeanConfigured() {
        MessageConverter converter = config.jsonMessageConverter();
        assertNotNull(converter);
    }
}
