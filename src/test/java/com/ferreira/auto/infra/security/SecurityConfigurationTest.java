package com.ferreira.auto.infra.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @Mock
    private SecurityFilter securityFilter;

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Test
    void securityFilterChainBuilds() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_SELF);
        DefaultSecurityFilterChain securityFilterChain = mock(DefaultSecurityFilterChain.class);

        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(securityFilterChain);

        SecurityFilterChain result = securityConfiguration.securityFilterChain(httpSecurity, securityFilter);

        assertSame(securityFilterChain, result);
    }

    @Test
    void authenticationManagerBeanDelegates() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager manager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(manager);

        assertSame(manager, securityConfiguration.authenticationManager(authenticationConfiguration));
    }

    @Test
    void passwordEncoderBeanCreated() {
        PasswordEncoder encoder = securityConfiguration.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("123", encoder.encode("123")));
    }
}
