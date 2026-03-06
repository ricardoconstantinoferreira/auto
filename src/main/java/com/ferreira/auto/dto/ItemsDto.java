package com.ferreira.auto.dto;

import java.io.Serializable;

public class ItemsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long modelId;

    public ItemsDto() {
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
