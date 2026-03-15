package com.ferreira.auto.exception;

public class PartialOrderPlacedException extends RuntimeException {
    private final String description;

    public PartialOrderPlacedException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
