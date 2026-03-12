package com.ferreira.auto.exception;

public class NoRemoveCategoryException extends RuntimeException{
    private final String description;

    public NoRemoveCategoryException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

