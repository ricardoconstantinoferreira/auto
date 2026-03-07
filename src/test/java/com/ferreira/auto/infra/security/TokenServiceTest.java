package com.ferreira.auto.infra.security;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private TokenService service;

    @Test
    void generateTokenReturnsTokenAndPersists() {
        ReflectionTestUtils.setField(service, "secret", "secret123");
        Customer customer = new Customer();
        customer.setEmail("a@b.com");

        String token = service.generateToken(customer);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        verify(customerService).saveToken(customer, token);
    }

    @Test
    void validateTokenReturnsEmailWhenValidAndMatching() {
        ReflectionTestUtils.setField(service, "secret", "secret123");
        Customer customer = new Customer();
        customer.setEmail("a@b.com");
        String token = service.generateToken(customer);
        customer.setToken(token);
        when(customerService.getCustomerByEmail("a@b.com")).thenReturn(customer);

        String email = service.validateToken(token);

        assertEquals("a@b.com", email);
    }

    @Test
    void validateTokenReturnsEmptyWhenSavedTokenDifferent() {
        ReflectionTestUtils.setField(service, "secret", "secret123");
        Customer customer = new Customer();
        customer.setEmail("a@b.com");
        String token = service.generateToken(customer);
        customer.setToken("other");
        when(customerService.getCustomerByEmail("a@b.com")).thenReturn(customer);

        String email = service.validateToken(token);

        assertEquals("", email);
    }

    @Test
    void validateTokenReturnsEmptyWhenInvalid() {
        ReflectionTestUtils.setField(service, "secret", "secret123");
        assertEquals("", service.validateToken("invalid.jwt"));
    }
}
