package com.ferreira.auto.exception;

public class CategoryAlreadyExistsException extends RuntimeException{
    private final String description;

    public CategoryAlreadyExistsException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
