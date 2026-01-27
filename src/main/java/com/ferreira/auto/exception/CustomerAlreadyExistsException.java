package com.ferreira.auto.exception;

public class CustomerAlreadyExistsException extends RuntimeException{
    private final String email;

    public CustomerAlreadyExistsException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
