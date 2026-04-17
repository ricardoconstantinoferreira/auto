package com.ferreira.auto.dto;

import jakarta.validation.constraints.NotNull;

public class ExpensesDto {

    private Long id;

    private Long expensesTypeId;

    private Long modelId;

    private Float value;

    public ExpensesDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpensesTypeId() {
        return expensesTypeId;
    }

    public void setExpensesTypeId(Long expensesTypeId) {
        this.expensesTypeId = expensesTypeId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
