package com.ferreira.auto.exception;

public class StockNoAvailableException extends RuntimeException {

    private final Long modelId;

    public StockNoAvailableException(String message, Long modelId) {
        super(message);
        this.modelId = modelId;
    }

    public Long getModelId() {
        return modelId;
    }
}

