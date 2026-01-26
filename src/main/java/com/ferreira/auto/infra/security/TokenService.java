package com.ferreira.auto.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private CustomerService customerService;

    public String generateToken(Customer customer) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            var username = customer.getEmail();

            String token = JWT.create()
                    .withIssuer("jwt_auto_automoveis")
                    .withSubject(username)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            if (!token.isEmpty()) {
                customerService.saveToken(customer, token);
            }

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error in the generating token", e);
        }
    }

    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            var email = JWT.require(algorithm)
                    .withIssuer("jwt_auto_automoveis")
                    .build()
                    .verify(token).getSubject();

            if (!email.isEmpty()) {
                Customer customer = customerService.getCustomerByEmail(email);

                if (!customer.getToken().equals(token)) {
                    return "";
                }
            }

            return email;


        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
