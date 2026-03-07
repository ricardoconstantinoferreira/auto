package com.ferreira.auto.infra.security;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDetailsServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerDetailsServiceImpl service;

    @Test
    void loadUserByUsernameReturnsUserDetails() {
        Customer customer = new Customer();
        customer.setPassword("pwd");
        when(customerRepository.findByEmail("x@y.com")).thenReturn(Optional.of(customer));

        UserDetails userDetails = service.loadUserByUsername("x@y.com");

        assertEquals("x@y.com", userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameThrowsWhenNotFound() {
        when(customerRepository.findByEmail("x@y.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("x@y.com"));
    }
}
