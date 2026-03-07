package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AuthDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.infra.security.TokenService;
import com.ferreira.auto.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthController controller;

    @Test
    void loginAuthenticatesAndReturnsCustomerWithToken() {
        AuthDto authDto = new AuthDto("test@mail.com", "pwd");
        Customer customer = new Customer();

        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));
        when(tokenService.generateToken(customer)).thenReturn("jwt");

        Customer response = controller.login(authDto);

        verify(authenticationManager).authenticate(any());
        assertEquals("jwt", response.getToken());
    }

    @Test
    void loginPropagatesAuthenticationFailure() {
        AuthDto authDto = new AuthDto("test@mail.com", "wrong");
        doThrow(new BadCredentialsException("bad")).when(authenticationManager).authenticate(any());

        assertThrows(BadCredentialsException.class, () -> controller.login(authDto));
    }
}
