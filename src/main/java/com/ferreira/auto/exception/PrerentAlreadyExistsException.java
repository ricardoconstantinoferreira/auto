package com.ferreira.auto.exception;

public class PrerentAlreadyExistsException extends RuntimeException {

    private final Long modelId;

    public PrerentAlreadyExistsException(String message, Long modelId) {
        super(message);
        this.modelId = modelId;
    }

    public Long getModelId() {
        return modelId;
    }
}
