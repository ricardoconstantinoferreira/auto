package com.ferreira.auto.exception;

public class CarmakerAlreadyExistsException extends RuntimeException{
    private final String description;

    public CarmakerAlreadyExistsException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
