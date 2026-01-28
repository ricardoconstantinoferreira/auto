package com.ferreira.auto.exception;

public class ModelAlreadyExistsException extends RuntimeException {
    private final String description;

    public ModelAlreadyExistsException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
