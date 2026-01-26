package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AuthDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.infra.security.TokenService;
import com.ferreira.auto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/auth/login")
    public String login(@RequestBody AuthDto authDto) {

        var usernamePassword =
                new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());

        authenticationManager.authenticate(usernamePassword);

        Optional<Customer> customerOptional =  customerRepository.findByEmail(authDto.email());
        Customer customer = customerOptional.get();

        return tokenService.generateToken(customer);
    }
}
