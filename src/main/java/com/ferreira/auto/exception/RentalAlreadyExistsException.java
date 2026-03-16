package com.ferreira.auto.exception;

public class RentalAlreadyExistsException extends RuntimeException {
    private final String description;

    public RentalAlreadyExistsException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
