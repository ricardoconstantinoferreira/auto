package com.ferreira.auto.dto;

public class PrerentExceptionDto {

    private String message;

    private Long modelId;

    public PrerentExceptionDto(String message, Long modelId) {
        this.message = message;
        this.modelId = modelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
