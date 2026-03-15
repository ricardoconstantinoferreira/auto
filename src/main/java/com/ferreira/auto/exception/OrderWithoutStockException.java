package com.ferreira.auto.exception;

public class OrderWithoutStockException extends RuntimeException {
    private final String description;

    public OrderWithoutStockException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
