package com.ferreira.auto.exception;

public class ValueEmptyNullException extends RuntimeException {
    private final String description;

    public ValueEmptyNullException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
